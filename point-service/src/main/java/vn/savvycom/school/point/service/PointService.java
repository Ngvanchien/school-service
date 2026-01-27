package vn.savvycom.school.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.savvycom.school.point.dtos.request.PointRequest;
import vn.savvycom.school.point.dtos.response.PointResponse;
import vn.savvycom.school.point.mapper.PointMapper;
import vn.savvycom.school.point.model.Point;
import vn.savvycom.school.point.repository.PointRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointRepository pointRepository;
  private final PointMapper pointMapper;

  // ================= LIST =================
  public List<PointResponse> list(Authentication a) {

    Map<?, ?> d = (Map<?, ?>) a.getDetails();
    String scope = String.valueOf(d.get("dataScope"));

    List<Point> points;

    switch (scope) {
      case "ALL" -> points = pointRepository.findAll();
      case "SCHOOL" -> {
        Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
        points = pointRepository.findBySchoolId(schoolId);
      }
      default -> { // SELF
        Long userId = Long.valueOf(a.getName());
        points = pointRepository.findByOwnerUserId(userId);
      }
    }

    return points.stream()
            .map(pointMapper::toResponse)
            .toList();
  }

  // ================= GET =================
  public PointResponse get(Authentication a, Long id) {

    Point p = pointRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(a, p)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    return pointMapper.toResponse(p);
  }

  // ================= CREATE =================
  public PointResponse create(Authentication a, PointRequest request) {

    Point p = pointMapper.toEntity(request);

    if (hasRole(a, "ADMIN")) {
      return pointMapper.toResponse(pointRepository.save(p));
    }

    Map<?, ?> d = (Map<?, ?>) a.getDetails();
    String scope = String.valueOf(d.get("dataScope"));

    if ("SCHOOL".equals(scope)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      p.setSchoolId(schoolId);
      p.setOwnerUserId(Long.valueOf(a.getName()));
      return pointMapper.toResponse(pointRepository.save(p));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }

  // ================= UPDATE =================
  public PointResponse update(Authentication a, Long id, PointRequest request) {

    Point p = pointRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(a, p)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    pointMapper.updateEntity(request, p);
    return pointMapper.toResponse(pointRepository.save(p));
  }

  // ================= DELETE =================
  public void delete(Authentication a, Long id) {

    Point p = pointRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(a, p)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    pointRepository.delete(p);
  }

  // ================= CHECK ACCESS =================
  private boolean canAccess(Authentication a, Point p) {

    if (hasRole(a, "ADMIN")) return true;

    Map<?, ?> d = (Map<?, ?>) a.getDetails();
    String scope = String.valueOf(d.get("dataScope"));

    if ("SCHOOL".equals(scope)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      return Objects.equals(p.getSchoolId(), schoolId);
    }

    Long userId = Long.valueOf(a.getName());
    return Objects.equals(userId, p.getOwnerUserId());
  }

  private boolean hasRole(Authentication a, String role) {
    return a.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_" + role));
  }
}

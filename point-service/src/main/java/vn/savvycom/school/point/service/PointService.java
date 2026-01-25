package vn.savvycom.school.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.savvycom.school.point.model.Point;
import vn.savvycom.school.point.repository.PointRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PointService {
  private final PointRepository pointRepository;

  public List<Point> list(Authentication a) {
    if (hasRole(a, "ADMIN")) return pointRepository.findAll();
    Map<?,?> d = (Map<?,?>) a.getDetails();
    String ds = String.valueOf(d.get("dataScope"));
    if ("SCHOOL".equals(ds)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      return pointRepository.findBySchoolId(schoolId);
    }
    Long uid = Long.valueOf(a.getName());
    return pointRepository.findByOwnerUserId(uid);
  }

  public Point create(Authentication a, Point p) {
    if (hasRole(a, "ADMIN")) return pointRepository.save(p);
    Map<?,?> d = (Map<?,?>) a.getDetails();
    String ds = String.valueOf(d.get("dataScope"));
    if ("SCHOOL".equals(ds)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      p.setSchoolId(schoolId);
      return pointRepository.save(p);
    }
    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }

  private boolean hasRole(Authentication a, String role) {
    return a.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_" + role));
  }
}

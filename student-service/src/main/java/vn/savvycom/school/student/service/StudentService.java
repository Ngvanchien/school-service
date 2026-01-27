package vn.savvycom.school.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.savvycom.school.student.dtos.request.StudentRequest;
import vn.savvycom.school.student.dtos.response.StudentResponse;
import vn.savvycom.school.student.mapper.StudentMapper;
import vn.savvycom.school.student.model.Student;
import vn.savvycom.school.student.repository.StudentRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;


  public List<StudentResponse> list(Authentication a) {

    Map<?, ?> d = (Map<?, ?>) a.getDetails();
    String scope = String.valueOf(d.get("dataScope"));

    List<Student> students;

    switch (scope) {
      case "ALL" -> {
        students = studentRepository.findAll();
      }
      case "SCHOOL" -> {
        Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
        students = studentRepository.findBySchoolId(schoolId);
      }
      default -> { // SELF
        Long userId = Long.valueOf(a.getName());
        students = studentRepository.findByOwnerUserId(userId);
      }
    }

    return students.stream()
            .map(studentMapper::toResponse)
            .toList();
  }


  public StudentResponse get(Authentication a, Long id) {

    Student s = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return studentMapper.toResponse(s);

  }

  public StudentResponse create(Authentication a, StudentRequest request) {

    Student s = studentMapper.toEntity(request);


    if (hasRole(a, "ADMIN")) {
      return studentMapper.toResponse(studentRepository.save(s));
    }

    Map<?, ?> d = (Map<?, ?>) a.getDetails();
    String scope = String.valueOf(d.get("dataScope"));

    if ("SCHOOL".equals(scope)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      s.setSchoolId(schoolId);
      s.setOwnerUserId(Long.valueOf(a.getName()));
      return studentMapper.toResponse(studentRepository.save(s));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }


  // ================= UPDATE =================
  public StudentResponse update(Authentication a, Long id, StudentRequest request) {

    Student s = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(a, s)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    studentMapper.updateEntity(request, s);
    return studentMapper.toResponse(studentRepository.save(s));
  }

  // ================= DELETE =================
  public void delete(Authentication a, Long id) {

    Student s = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(a, s)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    studentRepository.delete(s);
  }

  private boolean canAccess(Authentication a, Student s) {

    if (hasRole(a, "ADMIN")) return true;

    Map<?, ?> d = (Map<?, ?>) a.getDetails();
    String scope = String.valueOf(d.get("dataScope"));

    if ("SCHOOL".equals(scope)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      return Objects.equals(s.getSchoolId(), schoolId);
    }

    Long userId = Long.valueOf(a.getName());
    return Objects.equals(userId, s.getOwnerUserId());
  }


  private boolean hasRole(Authentication a, String role) {
    return a.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_" + role));
  }

}

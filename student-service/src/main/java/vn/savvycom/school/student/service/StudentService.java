package vn.savvycom.school.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.savvycom.school.student.model.Student;
import vn.savvycom.school.student.repository.StudentRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;

  public List<Student> list(Authentication a) {
    if (hasRole(a, "ADMIN")) {
      return studentRepository.findAll();
    }
    Map<?,?> d = (Map<?,?>) a.getDetails();
    String ds = String.valueOf(d.get("dataScope"));
    if ("SCHOOL".equals(ds)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      return studentRepository.findBySchoolId(schoolId);
    }
    Long uid = Long.valueOf(a.getName());
    return studentRepository.findByOwnerUserId(uid);
  }

  public Student get(Authentication a, Long id) {
    Student s = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    if (hasRole(a, "ADMIN")){
      return s;
    }
    Map<?,?> d = (Map<?,?>) a.getDetails();
    String ds = String.valueOf(d.get("dataScope"));
    if ("SCHOOL".equals(ds)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      if (Objects.equals(s.getSchoolId(), schoolId)) return s;
    }
    Long uid = Long.valueOf(a.getName());
    if (Objects.equals(uid, s.getOwnerUserId())) return s;
    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }

  public Student create(Authentication a, Student s) {
    if (hasRole(a, "ADMIN")) return studentRepository.save(s);
    Map<?,?> d = (Map<?,?>) a.getDetails();
    String ds = String.valueOf(d.get("dataScope"));
    if ("SCHOOL".equals(ds)) {
      Long schoolId = Long.valueOf(String.valueOf(d.get("schoolId")));
      s.setSchoolId(schoolId);
      return studentRepository.save(s);
    }
    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }

  private boolean hasRole(Authentication a, String role) {
    return a.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_" + role));
  }
}

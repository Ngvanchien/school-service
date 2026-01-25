package vn.savvycom.school.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.school.student.model.Student;
import vn.savvycom.school.student.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
  private final StudentService service;

  @GetMapping
  public List<Student> list(Authentication a) {
    return service.list(a);
  }

  @GetMapping("/{id}")
  public Student get(Authentication a, @PathVariable Long id) {
    return service.get(a, id);
  }

  @PostMapping
  public Student create(Authentication a, @RequestBody Student s) {
    return service.create(a, s);
  }
}

package vn.savvycom.school.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.school.student.dtos.request.StudentRequest;
import vn.savvycom.school.student.dtos.response.ResponseData;
import vn.savvycom.school.student.dtos.response.StudentResponse;
import vn.savvycom.school.student.service.StudentService;

import java.util.List;


@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
  @GetMapping
  public ResponseEntity<ResponseData<List<StudentResponse>>> list(Authentication a) {
    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Get student list successfully",
                    studentService.list(a)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
  @GetMapping("/{id}")
  public ResponseEntity<ResponseData<StudentResponse>> get(Authentication a,
                                                           @PathVariable("id") Long id) {
    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Get student successfully",
                    studentService.get(a, id)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
  @PostMapping
  public ResponseEntity<ResponseData<StudentResponse>> create(Authentication a,
                                                              @RequestBody StudentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseData<>(
                    HttpStatus.CREATED.value(),
                    "Create student successfully",
                    studentService.create(a, request)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseData<StudentResponse>> update(Authentication a,
                                                              @PathVariable("id") Long id,
                                                              @RequestBody StudentRequest request) {
    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Update student successfully",
                    studentService.update(a, id, request)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseData<Void>> delete(Authentication a,
                                                   @PathVariable("id") Long id) {
    studentService.delete(a, id);

    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Delete student successfully",
                    null
            )
    );
  }
}

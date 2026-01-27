package vn.savvycom.school.point.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.school.point.dtos.request.PointRequest;
import vn.savvycom.school.point.dtos.response.PointResponse;
import vn.savvycom.school.point.dtos.response.ResponseData;
import vn.savvycom.school.point.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

  private final PointService pointService;

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
  @GetMapping
  public ResponseEntity<ResponseData<List<PointResponse>>> list(Authentication a) {
    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Get point list successfully",
                    pointService.list(a)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
  @GetMapping("/{id}")
  public ResponseEntity<ResponseData<PointResponse>> get(Authentication a,
                                                         @PathVariable("id") Long id) {
    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Get point successfully",
                    pointService.get(a, id)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
  @PostMapping
  public ResponseEntity<ResponseData<PointResponse>> create(Authentication a,
                                                            @RequestBody PointRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseData<>(
                    HttpStatus.CREATED.value(),
                    "Create point successfully",
                    pointService.create(a, request)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseData<PointResponse>> update(Authentication a,
                                                            @PathVariable("id") Long id,
                                                            @RequestBody PointRequest request) {
    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Update point successfully",
                    pointService.update(a, id, request)
            )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseData<Void>> delete(Authentication a,
                                                   @PathVariable("id") Long id) {
    pointService.delete(a, id);

    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Delete point successfully",
                    null
            )
    );
  }
}

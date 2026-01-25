package vn.savvycom.school.point.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.school.point.model.Point;
import vn.savvycom.school.point.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {
  private final PointService service;

  @GetMapping
  public List<Point> list(Authentication a) { return service.list(a); }

  @PostMapping
  public Point create(Authentication a, @RequestBody Point p) { return service.create(a, p); }
}

package vn.savvycom.school.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.school.auth.service.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService auth;
  record LoginReq(String username, String password) {}
  record RefreshReq(String refreshToken) {}

  @PostMapping("/login")
  public Map<String,String> login(@RequestBody LoginReq req){

    return auth.login(req.username(), req.password());
  }

  @PostMapping("/refresh")
  public Map<String,String> refresh(@RequestBody RefreshReq req){

    return auth.refresh(req.refreshToken());
  }
}

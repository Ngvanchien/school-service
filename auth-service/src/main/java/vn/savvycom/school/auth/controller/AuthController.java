package vn.savvycom.school.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.school.auth.dtos.ResponseData;
import vn.savvycom.school.auth.service.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  public record LoginReq(String username, String password) {}
  public record RefreshReq(String refreshToken) {}

  @PostMapping("/login")
  public ResponseEntity<ResponseData<?>> login(@RequestBody LoginReq request) {
    log.info("Login with username: {}", request.username());

    Map<String, String> result =
            authService.login(request.username(), request.password());

    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Login successfully",
                    result
            )
    );
  }

  @PostMapping("/refresh")
  public ResponseEntity<ResponseData<?>> refresh(@RequestBody RefreshReq request) {
    log.info("Refresh token");

    Map<String, String> result =
            authService.refresh(request.refreshToken());

    return ResponseEntity.ok(
            new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Refresh token successfully",
                    result
            )
    );
  }
}

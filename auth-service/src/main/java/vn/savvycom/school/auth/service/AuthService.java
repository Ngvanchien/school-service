package vn.savvycom.school.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.savvycom.school.auth.model.RefreshToken;
import vn.savvycom.school.auth.model.User;
import vn.savvycom.school.auth.repository.RefreshTokenRepository;
import vn.savvycom.school.auth.repository.UserRepository;
import vn.savvycom.school.auth.config.JwtUtil;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtUtil jwt;
  private final RefreshTokenRepository refreshTokenRepository;

  @Value("${jwt.refreshTtl}") private long refreshTtl;

  public Map<String, String> login(String username, String password) {
    User u = userRepository.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    if (!Boolean.TRUE.equals(u.getEnabled()) || !encoder.matches(password, u.getPassword()))
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    String access = jwt.generateAccessToken(u);
    RefreshToken rt = new RefreshToken();
    rt.setToken(UUID.randomUUID().toString());
    rt.setUser(u);
    rt.setExpiresAt(Instant.now().plusSeconds(refreshTtl));
    refreshTokenRepository.save(rt);
    return Map.of("accessToken", access, "refreshToken", rt.getToken(), "tokenType", "Bearer");
  }

  public Map<String, String> refresh(String refreshToken) {
    RefreshToken rt = refreshTokenRepository.findByTokenAndRevokedFalse(refreshToken)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    if (rt.getExpiresAt().isBefore(Instant.now())) {
      rt.setRevoked(true); refreshTokenRepository.save(rt);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh expired");
    }
    String access = jwt.generateAccessToken(rt.getUser());
    return Map.of("accessToken", access, "tokenType", "Bearer");
  }
}

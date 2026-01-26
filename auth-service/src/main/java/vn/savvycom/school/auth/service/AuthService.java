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
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final RefreshTokenRepository refreshTokenRepository;

  @Value("${jwt.refreshTtl}")
  private long refreshTokenTtl;

  public Map<String, String> login(String username, String password) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    if (!Boolean.TRUE.equals(user.getEnabled())
            || !passwordEncoder.matches(password, user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    String accessToken = jwtUtil.generateAccessToken(user);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setUser(user);
    refreshToken.setExpiresAt(Instant.now().plusSeconds(refreshTokenTtl));

    refreshTokenRepository.save(refreshToken);

    return Map.of(
            "accessToken", accessToken,
            "refreshToken", refreshToken.getToken(),
            "tokenType", "Bearer"
    );
  }


  public Map<String, String> refresh(String refreshTokenValue) {

    RefreshToken refreshToken = refreshTokenRepository
            .findByTokenAndRevokedFalse(refreshTokenValue)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
      refreshToken.setRevoked(true);
      refreshTokenRepository.save(refreshToken);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh expired");
    }

    String newAccessToken = jwtUtil.generateAccessToken(refreshToken.getUser());

    return Map.of(
            "accessToken", newAccessToken,
            "tokenType", "Bearer"
    );
  }
}


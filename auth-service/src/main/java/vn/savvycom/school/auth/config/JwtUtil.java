package vn.savvycom.school.auth.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.savvycom.school.auth.model.*;

import java.time.Instant;
import java.util.*;

@Component
public class JwtUtil {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.issuer}") private String issuer;
  @Value("${jwt.accessTtl}") private long accessTtl;

  public String generateAccessToken(User user) {
    List<String> roles = user.getRoles().stream().map(Role::getName).toList();
    DataScopeType maxScope = user.getRoles().stream().map(Role::getDataScope)
        .max(Comparator.comparingInt(Enum::ordinal)).orElse(DataScopeType.SELF);
    return Jwts.builder()
        .setSubject(String.valueOf(user.getId()))
        .setIssuer(issuer)
        .claim("username", user.getUsername())
        .claim("roles", roles)
        .claim("schoolId", user.getSchoolId())
        .claim("studentId", user.getStudentId())
        .claim("dataScope", maxScope.name())
        .setExpiration(Date.from(Instant.now().plusSeconds(accessTtl)))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }
}

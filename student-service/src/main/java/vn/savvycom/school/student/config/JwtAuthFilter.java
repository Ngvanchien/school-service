package vn.savvycom.school.student.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.issuer}") private String issuer;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
    if (auth != null && auth.startsWith("Bearer ")) {
      try {
        Claims c = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
            .requireIssuer(issuer).build().parseClaimsJws(auth.substring(7)).getBody();

        List<SimpleGrantedAuthority> auths = ((List<String>) c.get("roles")).stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .toList();

        UsernamePasswordAuthenticationToken at =
            new UsernamePasswordAuthenticationToken(c.getSubject(), null, auths);
        Map<String,Object> details = new HashMap<>();
        details.put("username", c.get("username"));
        details.put("schoolId", c.get("schoolId"));
        details.put("dataScope", c.get("dataScope"));
        at.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(at);
      } catch (Exception ignored) {}
    }
    chain.doFilter(req, res);
  }
}

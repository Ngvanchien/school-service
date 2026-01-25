package vn.savvycom.school.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.issuer}") private String issuer;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getPath().toString();

    if (path.contains("/api/auth/")) {
      return chain.filter(exchange);
    }


    String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (auth == null || !auth.startsWith("Bearer ")) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    try {
      String token = auth.substring(7);
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
          .requireIssuer(issuer)
          .build().parseClaimsJws(token).getBody();

      ServerHttpRequest mutated = exchange.getRequest().mutate()
          .header("X-User-Id", claims.getSubject())
          .header("X-Roles", String.join(",", (List<String>) claims.get("roles")))
          .header("X-School-Id", String.valueOf(claims.get("schoolId")))
          .header("X-Data-Scope", String.valueOf(claims.get("dataScope")))
          .build();
      return chain.filter(exchange.mutate().request(mutated).build());
    } catch (Exception e) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
  }

  @Override public int getOrder() { return -1; }
}

package vn.savvycom.school.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwt) throws Exception {
    http.csrf(csrf->csrf.disable())
      .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
      .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

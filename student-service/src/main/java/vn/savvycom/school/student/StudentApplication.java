package vn.savvycom.school.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class StudentApplication {
  public static void main(String[] args) { SpringApplication.run(StudentApplication.class, args); }
}

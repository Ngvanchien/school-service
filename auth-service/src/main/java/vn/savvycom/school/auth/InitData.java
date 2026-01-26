package vn.savvycom.school.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.savvycom.school.auth.model.*;
import vn.savvycom.school.auth.repository.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InitData {

  private final PasswordEncoder encoder;

  @Bean
  CommandLineRunner seed(
          UserRepository userRepository,
          RoleRepository roleRepository,
          PermissionRepository permissionRepository
  ) {
    return args -> {

      if (roleRepository.count() == 0) {

        /* ========= PERMISSIONS ========= */
        Permission studentReadPermission = new Permission();
        studentReadPermission.setCode("STUDENT_READ");

        Permission studentWritePermission = new Permission();
        studentWritePermission.setCode("STUDENT_WRITE");

        Permission pointReadPermission = new Permission();
        pointReadPermission.setCode("POINT_READ");

        permissionRepository.saveAll(List.of(
                studentReadPermission,
                studentWritePermission,
                pointReadPermission
        ));

        /* ========= ROLES ========= */
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setDataScope(DataScopeType.ALL);
        adminRole.getPermissions().addAll(permissionRepository.findAll());

        Role managerRole = new Role();
        managerRole.setName("SCHOOL_MANAGER");
        managerRole.setDataScope(DataScopeType.SCHOOL);
        managerRole.getPermissions().addAll(permissionRepository.findAll());

        Role studentRole = new Role();
        studentRole.setName("STUDENT");
        studentRole.setDataScope(DataScopeType.SELF);

        roleRepository.saveAll(List.of(adminRole, managerRole, studentRole));

        /* ========= USERS ========= */
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(encoder.encode("admin123"));
        adminUser.getRoles().add(adminRole);
        userRepository.save(adminUser);

        User managerUser = new User();
        managerUser.setUsername("manager1");
        managerUser.setPassword(encoder.encode("manager123"));
        managerUser.setSchoolId(100L);
        managerUser.getRoles().add(managerRole);
        userRepository.save(managerUser);

        User studentUser = new User();
        studentUser.setUsername("student1");
        studentUser.setPassword(encoder.encode("student123"));
        studentUser.setSchoolId(100L);
        studentUser.setStudentId(3L);
        studentUser.getRoles().add(studentRole);
        userRepository.save(studentUser);
      }
    };
  }
}


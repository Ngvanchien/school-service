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
  CommandLineRunner seed(UserRepository users, RoleRepository roles, PermissionRepository perms){
    return args -> {
      if (roles.count() == 0) {
        // Permissions (simple demo)
        Permission pRead = new Permission(); pRead.setCode("STUDENT_READ");
        Permission pWrite = new Permission(); pWrite.setCode("STUDENT_WRITE");
        Permission pPoint = new Permission(); pPoint.setCode("POINT_READ");
        perms.saveAll(List.of(pRead, pWrite, pPoint));

        Role admin = new Role(); admin.setName("ADMIN"); admin.setDataScope(DataScopeType.ALL);
        admin.getPermissions().addAll(perms.findAll());
        Role manager = new Role(); manager.setName("SCHOOL_MANAGER"); manager.setDataScope(DataScopeType.SCHOOL);
        manager.getPermissions().addAll(perms.findAll());
        Role student = new Role(); student.setName("STUDENT"); student.setDataScope(DataScopeType.SELF);
        roles.saveAll(List.of(admin, manager, student));

        User uAdmin = new User(); uAdmin.setUsername("admin"); uAdmin.setPassword(encoder.encode("admin123"));
        uAdmin.getRoles().add(admin);
        users.save(uAdmin);

        User uMgr = new User(); uMgr.setUsername("manager1"); uMgr.setPassword(encoder.encode("manager123"));
        uMgr.setSchoolId(100L); uMgr.getRoles().add(manager);
        users.save(uMgr);

        User uStu = new User(); uStu.setUsername("student1"); uStu.setPassword(encoder.encode("student123"));
        uStu.setSchoolId(100L); uStu.getRoles().add(student);
        users.save(uStu);
      }
    };
  }
}

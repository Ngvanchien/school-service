package vn.savvycom.school.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.school.auth.model.Permission;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
  Optional<Permission> findByCode(String code);
}

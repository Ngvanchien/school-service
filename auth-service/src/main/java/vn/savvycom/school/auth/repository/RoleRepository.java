package vn.savvycom.school.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.school.auth.model.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
}

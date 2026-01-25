package vn.savvycom.school.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.school.auth.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}

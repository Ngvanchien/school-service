package vn.savvycom.school.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.school.auth.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByTokenAndRevokedFalse(String token);
}

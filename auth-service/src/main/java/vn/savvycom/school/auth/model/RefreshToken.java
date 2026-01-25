package vn.savvycom.school.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name="refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String token;
  @ManyToOne(optional = false)
  private User user;
  private Instant expiresAt;
  private boolean revoked = false;
}

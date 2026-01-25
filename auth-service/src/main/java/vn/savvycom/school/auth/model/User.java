package vn.savvycom.school.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;
  private Long schoolId;
  private Boolean enabled = true;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="user_roles",
    joinColumns=@JoinColumn(name="user_id"),
    inverseJoinColumns=@JoinColumn(name="role_id"))
  private Set<Role> roles = new HashSet<>();
}

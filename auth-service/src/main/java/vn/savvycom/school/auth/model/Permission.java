package vn.savvycom.school.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String code;
}

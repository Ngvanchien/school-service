package vn.savvycom.school.point.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="points")
@Getter
@Setter
@NoArgsConstructor
public class Point {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long studentId;

  private Long subjectId;

  private Long schoolId;

  private Long ownerUserId;

  private Double value;

  private String pointType;

  private String semester;
}

package vn.savvycom.school.point.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointResponse {

    private Long id;

    private Long studentId;

    private Long schoolId;

    private Long subjectId;

    private Double value;

    private String pointType;

    private String semester;
}

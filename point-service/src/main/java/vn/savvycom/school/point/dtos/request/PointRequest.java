package vn.savvycom.school.point.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointRequest {

    private Long studentId;

    private Long schoolId;

    private Long ownerUserId;

    private Long subjectId;

    private Double value;

    private String pointType;

    private String semester;
}

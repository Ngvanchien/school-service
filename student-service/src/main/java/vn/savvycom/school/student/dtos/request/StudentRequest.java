package vn.savvycom.school.student.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudentRequest {

    private String fullName;

    private String studentCode;

    private String email;

    private String phone;

    private String gender;

    private Long schoolId;

    private Long ownerUserId;
}

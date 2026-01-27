package vn.savvycom.school.student.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;

    private String fullName;

    private String studentCode;

    private String email;

    private String phone;

    private String gender;

    private Long schoolId;

    private Long ownerUserId;
}

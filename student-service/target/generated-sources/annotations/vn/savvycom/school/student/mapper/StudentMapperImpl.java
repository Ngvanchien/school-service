package vn.savvycom.school.student.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.savvycom.school.student.dtos.request.StudentRequest;
import vn.savvycom.school.student.dtos.response.StudentResponse;
import vn.savvycom.school.student.model.Student;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T16:51:19+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(StudentRequest request) {
        if ( request == null ) {
            return null;
        }

        Student student = new Student();

        student.setFullName( request.getFullName() );
        student.setStudentCode( request.getStudentCode() );
        student.setEmail( request.getEmail() );
        student.setPhone( request.getPhone() );
        student.setGender( request.getGender() );
        student.setSchoolId( request.getSchoolId() );
        student.setOwnerUserId( request.getOwnerUserId() );

        return student;
    }

    @Override
    public StudentResponse toResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse.StudentResponseBuilder studentResponse = StudentResponse.builder();

        studentResponse.id( student.getId() );
        studentResponse.fullName( student.getFullName() );
        studentResponse.studentCode( student.getStudentCode() );
        studentResponse.email( student.getEmail() );
        studentResponse.phone( student.getPhone() );
        studentResponse.gender( student.getGender() );
        studentResponse.schoolId( student.getSchoolId() );
        studentResponse.ownerUserId( student.getOwnerUserId() );

        return studentResponse.build();
    }

    @Override
    public void updateEntity(StudentRequest request, Student student) {
        if ( request == null ) {
            return;
        }

        if ( request.getFullName() != null ) {
            student.setFullName( request.getFullName() );
        }
        if ( request.getStudentCode() != null ) {
            student.setStudentCode( request.getStudentCode() );
        }
        if ( request.getEmail() != null ) {
            student.setEmail( request.getEmail() );
        }
        if ( request.getPhone() != null ) {
            student.setPhone( request.getPhone() );
        }
        if ( request.getGender() != null ) {
            student.setGender( request.getGender() );
        }
        if ( request.getSchoolId() != null ) {
            student.setSchoolId( request.getSchoolId() );
        }
        if ( request.getOwnerUserId() != null ) {
            student.setOwnerUserId( request.getOwnerUserId() );
        }
    }
}

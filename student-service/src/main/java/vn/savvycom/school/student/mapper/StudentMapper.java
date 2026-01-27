package vn.savvycom.school.student.mapper;

import org.mapstruct.*;
import vn.savvycom.school.student.dtos.request.StudentRequest;
import vn.savvycom.school.student.dtos.response.StudentResponse;
import vn.savvycom.school.student.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentRequest request);

    StudentResponse toResponse(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(StudentRequest request, @MappingTarget Student student);
}

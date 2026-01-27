package vn.savvycom.school.point.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.savvycom.school.point.dtos.request.PointRequest;
import vn.savvycom.school.point.dtos.response.PointResponse;
import vn.savvycom.school.point.model.Point;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T16:51:20+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class PointMapperImpl implements PointMapper {

    @Override
    public Point toEntity(PointRequest request) {
        if ( request == null ) {
            return null;
        }

        Point point = new Point();

        point.setStudentId( request.getStudentId() );
        point.setSubjectId( request.getSubjectId() );
        point.setValue( request.getValue() );
        point.setPointType( request.getPointType() );
        point.setSemester( request.getSemester() );

        return point;
    }

    @Override
    public PointResponse toResponse(Point point) {
        if ( point == null ) {
            return null;
        }

        PointResponse.PointResponseBuilder pointResponse = PointResponse.builder();

        pointResponse.id( point.getId() );
        pointResponse.studentId( point.getStudentId() );
        pointResponse.subjectId( point.getSubjectId() );
        pointResponse.value( point.getValue() );
        pointResponse.pointType( point.getPointType() );
        pointResponse.semester( point.getSemester() );

        return pointResponse.build();
    }

    @Override
    public void updateEntity(PointRequest request, Point point) {
        if ( request == null ) {
            return;
        }

        if ( request.getStudentId() != null ) {
            point.setStudentId( request.getStudentId() );
        }
        if ( request.getSubjectId() != null ) {
            point.setSubjectId( request.getSubjectId() );
        }
        if ( request.getValue() != null ) {
            point.setValue( request.getValue() );
        }
        if ( request.getPointType() != null ) {
            point.setPointType( request.getPointType() );
        }
        if ( request.getSemester() != null ) {
            point.setSemester( request.getSemester() );
        }
    }
}

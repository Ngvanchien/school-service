package vn.savvycom.school.point.mapper;

import org.mapstruct.*;
import vn.savvycom.school.point.dtos.request.PointRequest;
import vn.savvycom.school.point.dtos.response.PointResponse;
import vn.savvycom.school.point.model.Point;

@Mapper(componentModel = "spring")
public interface PointMapper {

    Point toEntity(PointRequest request);

    PointResponse toResponse(Point point);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PointRequest request, @MappingTarget Point point);
}

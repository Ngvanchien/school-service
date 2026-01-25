package vn.savvycom.school.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.school.point.model.Point;
import java.util.*;

public interface PointRepository extends JpaRepository<Point, Long> {
  List<Point> findBySchoolId(Long schoolId);
  List<Point> findByOwnerUserId(Long ownerUserId);
  List<Point> findByStudentId(Long studentId);
}

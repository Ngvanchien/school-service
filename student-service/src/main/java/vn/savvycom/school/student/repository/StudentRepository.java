package vn.savvycom.school.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.school.student.model.Student;
import java.util.*;

public interface StudentRepository extends JpaRepository<Student, Long> {
  List<Student> findBySchoolId(Long schoolId);
  List<Student> findByOwnerUserId(Long ownerUserId);
}

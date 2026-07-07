package in.coderarmy.crudSpringBootDemo.Repository;

import in.coderarmy.crudSpringBootDemo.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
List<Student> findByDeletedIsFalse();
Optional<Student> findByIdAndDeletedIsFalse(Long aLong);
}

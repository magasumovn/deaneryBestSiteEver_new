package magasumovn.deanery.repo;

import magasumovn.deanery.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}

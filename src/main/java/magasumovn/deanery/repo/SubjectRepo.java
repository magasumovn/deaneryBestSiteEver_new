package magasumovn.deanery.repo;

import magasumovn.deanery.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    List<Subject> findByCourseName(String courseName);
}

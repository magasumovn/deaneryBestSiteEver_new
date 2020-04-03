package magasumovn.deanery.controllers;

import magasumovn.deanery.model.Subject;
import magasumovn.deanery.repo.SubjectRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subject")
public class SubjectController {
    private final SubjectRepo subjectRepo;

    @Autowired
    public SubjectController(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @GetMapping
    public List<Subject> list(@RequestParam(required = false, defaultValue = "") String course) {
        if (course.equals("") || course.equals("Все")) {
            return subjectRepo.findAll();
        }

        return subjectRepo.findByCourseName(course);
    }

    @GetMapping("{id}")
    public Subject getOne(@PathVariable("id") Subject subject) {
        return subject;
    }

    @PostMapping
    public Subject create(@RequestBody Subject subject) {
        return subjectRepo.save(subject);
    }

    @PutMapping("{id}")
    public Subject update(
            @PathVariable("id") Subject subjectFromDb,
            @RequestBody Subject subject) {
        BeanUtils.copyProperties(subject, subjectFromDb, "subjectID");
        return subjectRepo.save(subjectFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Subject subject) {
        subjectRepo.delete(subject);
    }
}

package magasumovn.deanery.model;

import javax.persistence.*;

@Entity
@Table
public class Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subjectID;

    private String subjectName;

    private String courseName;

    public Long getSubjectID() {
        return subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}


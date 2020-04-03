package magasumovn.deanery.model;

import javax.persistence.*;

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentID;

    private String studentName;

    private String passport;
    private String phoneNumber;

    @ManyToOne()
    @JoinColumn(name = "groupNumber")
    private Group group;

    public Long getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Group getGroup() {
        return group;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

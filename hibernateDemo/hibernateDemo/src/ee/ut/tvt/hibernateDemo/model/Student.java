package ee.ut.tvt.hibernateDemo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @ManyToMany
    @JoinTable(
        name = "STUDENTS_TO_COURSES",
        joinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    )
    private Set<Course> courses;

    @ManyToOne
    @JoinColumn(name = "SPECIALITY_ID", nullable = false)
    private Speciality speciality;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" -- " + firstName + " " + lastName + "\n");
        sb.append("      Participates in the following courses: \n");
        if (courses != null) {
            for (Course c : courses) {
                sb.append("       * " + c.getEstonianName() + "\n");
            }
        } else {
            sb.append("       ?");
        }

        return sb.toString();
    }

    public String getFullName() {
        return (lastName == null ? firstName : firstName + " " + lastName);
    }

}

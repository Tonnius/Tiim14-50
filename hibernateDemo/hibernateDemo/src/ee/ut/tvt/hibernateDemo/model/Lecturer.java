package ee.ut.tvt.hibernateDemo.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "LECTURER")
public class Lecturer {

	public static final Format FORMATTER = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Transient
    private Date birthDate;
    
    @Transient
    private String academicDegree;

    @Transient
    private Set<Course> courses;

    public Set<Course> getCourses() {
        return courses;
    }
    
    private String getBirthDateString() {
        return birthDate == null ? "?" : FORMATTER.format(birthDate);  // birthDate.toString();
    }

    private String getDegreeString() {
        return academicDegree == null ? "?" : academicDegree;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" -- " + firstName + " " + lastName + " (degree: " + getDegreeString() + ", date of birth: " + getBirthDateString() + ")\n");
        sb.append("      Teaches following courses: \n");
        if (courses != null) {
            for (Course c : courses) {
                sb.append("       * " + c.getEstonianName() + "\n");
            }
        } else {
            sb.append("       ?");
        }
        
        return sb.toString();
    }


}
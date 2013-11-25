package ee.ut.tvt.hibernateDemo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estonian_name")
    private String estonianName;

    @Transient
    private String englishName;

    @Transient
    private Lecturer lecturer;
    
    @ManyToMany(mappedBy="courses")
    private Set<Student> students;
    
    @Transient
    private Set<Speciality> specialities;
    
    public Lecturer getLecturer() {
        return lecturer;
    }

    public String getEstonianName() {
        return estonianName;
    }
    
    private String getEnglishName() {
        return englishName == null ? "?" : englishName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" -- " + estonianName + " [" + getEnglishName() + "]\n");
        sb.append("      Belongs to following specialities: \n");
        if (specialities != null) {
            for (Speciality s : specialities) {
                sb.append("       * " + s.getName() + "\n");
            }
        } else {
            sb.append("       ?");
        }

        return sb.toString();
    }

}
package ee.ut.tvt.hibernateDemo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SPECIALITY")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "name")
	private String name;

	@Transient
	private String department;
	
	@Transient
    private Set<Course> courses;
    
    @OneToMany(mappedBy = "speciality")
    private Set<Student> students;
	
    public String getName() {
        return name;
    }
    
    private String getDepartmentString() {
        return department == null ? "?" : department;
    }
    
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" -- " + name + " (faculty : " + getDepartmentString() + ")\n");
        sb.append("      Has the following students: \n");
        if (students != null) {
            for (Student s : students) {
                sb.append("       * " + s.getFullName() + "\n");
            }
        } else {
            sb.append("       ?");
        }

        return sb.toString();
	}

}

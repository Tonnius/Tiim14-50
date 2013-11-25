package ee.ut.tvt.hibernateDemo;

import java.util.List;

import ee.ut.tvt.hibernateDemo.model.Course;
import ee.ut.tvt.hibernateDemo.model.Lecturer;
import ee.ut.tvt.hibernateDemo.model.Speciality;
import ee.ut.tvt.hibernateDemo.model.Student;
import ee.ut.tvt.hibernateDemo.service.HibernateDataService;

public class Main {

    public static void main(String[] args) {
        
        HibernateDataService service = new HibernateDataService();
        
        List<Student> students = service.getStudents();
        List<Course> courses = service.getCourses();
        List<Lecturer> lecturers = service.getLecturers();
        List<Speciality> specialities = service.getSpecialities();

        
        echo("\n\n\n\n" +
             " ==================================================================== \n" +
             " =====                                                          ===== \n" +
             " =====                   DATABASE CONTENTS                      ===== \n" +
             " =====                                                          ===== \n" +
             " ==================================================================== \n"
        );
        
        
        echo("\n\n\n\n" +
             " ********************************************** \n" +
             " **              STUDENTS                    ** \n" +
             " ********************************************** \n" +
             "\n"
        );
        
        echo("Found " + students.size() + " students:");
        for (Student s : students) {
            echo(s);
        }

        
        
        
        
        echo("\n\n\n\n" +
             " ********************************************** \n" +
             " **              LECTURERS                   ** \n" +
             " ********************************************** \n" +
             "\n"
        );

        echo("Found " + students.size() + " lecturers:");
        for (Lecturer l : lecturers) {
            echo(l);
        }
        
        
        
        
        
        echo("\n\n\n\n" +
             " ********************************************** \n" +
             " **               COURSES                    ** \n" +
             " ********************************************** \n" +
             "\n"
        );        

        echo("Found " + courses.size() + " courses:");
        for (Course c : courses) {
            echo(c);
        }
        
        
        
        
        echo("\n\n\n\n" +
             " ********************************************** \n" +
             " **              SPECIALITIES                ** \n" +
             " ********************************************** \n" +
             "\n"
        );                

        echo("Found " + students.size() + " specialities:");
        for (Speciality s : specialities) {
            echo(s);
        }
        
        
    }
    
    
    private static void echo(Object o) {
        System.out.println(o);
    }
    
}
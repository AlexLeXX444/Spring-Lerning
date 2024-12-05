package my.app.homework.repository;

import lombok.Getter;
import my.app.homework.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class StudentRepository {

    private final List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
        int k = 1;
        for (int i = 1; i <= 10; i++) {
            if (i == 6) {
                k++;
            }
            this.students.add(new Student("Student " + i, "Group-" + k));
        }
    }

    /**
     * POST new student. Do nothing if already exist.
     * @param student with parameters {name, group name}.
     * @return student if created or null if exists.
     */
    public Student addStudent(Student student) {
        if (this.getStudentByName(student.getName()) == null) {
            this.students.add(student);
            
            return student;
        }

        return null;
    }

    /**
     * GET by student name.
     * @param name in string variable.
     * @return student if exists or null if not.
     */
    public Student getStudentByName (String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    /**
     * GET by student id.
     * @param id in numeric variable.
     * @return student if exists or null if not.
     */
    public Student getStudentById(long id) {
        return students
                .stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * GET by student group name.
     * @param groupName in string variable.
     * @return list of students if exists or empty list if not.
     */
    public List<Student> getStudentsByGroupName(String groupName) {
        List<Student> returnStudents = new ArrayList<>();

        for (Student student : this.students) {
            if (student.getGroupName().equals(groupName)) {
                returnStudents.add(student);
            }
        }

        return returnStudents;
    }

    /**
     * GET list of all students.
     * @return list of students if exists or empty list if not.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * DELETE student by id.
     * @param id in numeric variable.
     * @return return and remove student from list if exists or return null.
     */
    public Student deleteStudentById(long id) {
        Student student = this.getStudentById(id);
        if (student != null) {
            this.students.remove(student);
            return student;
        }

        return null;
    }
}

package my.app.homework.controller;

import my.app.homework.model.Student;
import my.app.homework.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable long id) {
        return studentRepository.getStudentById(id);
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    @GetMapping("/student/search")
    public Student getStudentByName(@RequestParam String name) {
         return studentRepository.getStudentByName(name);
    }

    @GetMapping("/group/{groupName}/students")
    public List<Student> getStudentsByGroupName(@PathVariable String groupName) {
        return studentRepository.getStudentsByGroupName(groupName);
    }

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.addStudent(student);
    }

    @DeleteMapping("/student/{id}")
    public Student deleteStudent(@PathVariable long id) {
        return studentRepository.deleteStudentById(id);
    }
}

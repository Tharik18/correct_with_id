package com.example.project_111.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_111.Entity.Student;
import com.example.project_111.Service.StudentService;

@RestController
@RequestMapping("/api/registration")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register/student")
    public String registerStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}

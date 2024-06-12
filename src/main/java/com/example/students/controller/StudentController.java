package com.example.students.controller;
import com.example.students.model.Student;
import com.example.students.repository.StudentRepository;
import com.example.students.resource.StudentRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentRepository repository;
    @GetMapping()
    public ResponseEntity<List<Student>> findAllStudents () {

        return ResponseEntity.ok(this.repository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Student> saveStudent(@RequestBody StudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setDateOfBirth(request.getDateOfBirth());
        return ResponseEntity.status(201).body(this.repository.save(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity getStudentById(@PathVariable String id) {

        Optional<Student> student = this.repository.findById(id);

        if(student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.ok("The student with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudentById(@PathVariable String id) {

        Optional<Student> student = this.repository.findById(id);

        if(student.isPresent()) {
            this.repository.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The student with id: " + id + " was not found.");
        }
    }


}

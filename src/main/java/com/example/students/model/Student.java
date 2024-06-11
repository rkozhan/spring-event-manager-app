package com.example.students.model;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.time.LocalDate;

@Data
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private String email;
    private int age;
}

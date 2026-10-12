package com.dhruv.placementmanagement.service;

import com.dhruv.placementmanagement.entity.Student;
import com.dhruv.placementmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getEligibleStudents(Double minCgpa) {
        return studentRepository.findByCgpaGreaterThanEqual(minCgpa);
    }

    public List<Student> getStudentsByBranch(String branch) {
        return studentRepository.findByBranch(branch);
    }

    public Student saveStudent(Student student) {
        if (student.getId() == null && studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists.");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updated) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setBranch(updated.getBranch());
                    existing.setCgpa(updated.getCgpa());
                    existing.setGraduationYear(updated.getGraduationYear());
                    return studentRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}

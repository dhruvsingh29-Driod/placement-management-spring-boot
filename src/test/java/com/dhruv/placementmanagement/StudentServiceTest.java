package com.dhruv.placementmanagement;

import com.dhruv.placementmanagement.entity.Student;
import com.dhruv.placementmanagement.repository.StudentRepository;
import com.dhruv.placementmanagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        sampleStudent = new Student("Ananya Rao", "ananya@vit.edu", "CSE", 8.7, "2026");
        sampleStudent.setId(1L);
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        Optional<Student> result = studentService.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ananya Rao", result.get().getName());
        assertEquals("ananya@vit.edu", result.get().getEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveStudent_Success() {
        Student newStudent = new Student("Rahul Verma", "rahul@vit.edu", "IT", 7.8, "2026");
        when(studentRepository.existsByEmail("rahul@vit.edu")).thenReturn(false);
        when(studentRepository.save(newStudent)).thenReturn(newStudent);

        Student saved = studentService.saveStudent(newStudent);

        assertNotNull(saved);
        assertEquals("rahul@vit.edu", saved.getEmail());
        verify(studentRepository, times(1)).save(newStudent);
    }

    @Test
    void testSaveStudent_DuplicateEmail_ThrowsException() {
        Student duplicate = new Student("Fake Student", "ananya@vit.edu", "CSE", 7.0, "2026");
        when(studentRepository.existsByEmail("ananya@vit.edu")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> studentService.saveStudent(duplicate));
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testGetEligibleStudents_ReturnsCutoffMetStudents() {
        Student s1 = new Student("S1", "s1@vit.edu", "CSE", 8.5, "2026");
        Student s2 = new Student("S2", "s2@vit.edu", "IT", 7.2, "2026");
        when(studentRepository.findByCgpaGreaterThanEqual(7.0)).thenReturn(Arrays.asList(s1, s2));

        List<Student> eligible = studentService.getEligibleStudents(7.0);

        assertEquals(2, eligible.size());
        verify(studentRepository, times(1)).findByCgpaGreaterThanEqual(7.0);
    }
}

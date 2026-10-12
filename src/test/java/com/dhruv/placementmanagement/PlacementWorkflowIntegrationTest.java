package com.dhruv.placementmanagement;

import com.dhruv.placementmanagement.entity.Company;
import com.dhruv.placementmanagement.entity.JobApplication;
import com.dhruv.placementmanagement.entity.PlacementDrive;
import com.dhruv.placementmanagement.entity.Student;
import com.dhruv.placementmanagement.repository.CompanyRepository;
import com.dhruv.placementmanagement.repository.JobApplicationRepository;
import com.dhruv.placementmanagement.repository.PlacementDriveRepository;
import com.dhruv.placementmanagement.repository.StudentRepository;
import com.dhruv.placementmanagement.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlacementWorkflowIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PlacementDriveRepository driveRepository;

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private JobApplicationService applicationService;

    @Test
    void testEndToEndEligibilityAndApplicationWorkflow() {
        // 1. Setup Student
        Student student = new Student("Neha Sharma", "neha@vit.edu", "CSE", 8.2, "2026");
        student = studentRepository.save(student);

        // 2. Setup Company & Drive
        Company company = new Company("Google", "recruiting@google.com", "Big Tech", "Bangalore");
        company = companyRepository.save(company);

        PlacementDrive drive = new PlacementDrive(company, "Software Engineer",
                "Backend engineering role", 7.5, "CSE,IT", 18.0, LocalDate.now().plusDays(15), "OPEN");
        drive = driveRepository.save(drive);

        // 3. Eligible Application Succeeds
        JobApplication app = applicationService.apply(student.getId(), drive.getId());
        assertNotNull(app);
        assertNotNull(app.getId());
        assertEquals("APPLIED", app.getStatus());

        // 4. Duplicate Application is Prevented
        final Long studentId = student.getId();
        final Long driveId = drive.getId();
        assertThrows(IllegalStateException.class, () -> applicationService.apply(studentId, driveId));

        // 5. Ineligible Student (Low CGPA) is Rejected
        Student lowCgpaStudent = new Student("Amit Kumar", "amit@vit.edu", "CSE", 6.8, "2026");
        lowCgpaStudent = studentRepository.save(lowCgpaStudent);
        final Long lowStudentId = lowCgpaStudent.getId();

        assertThrows(IllegalStateException.class, () -> applicationService.apply(lowStudentId, driveId));

        // 6. Status Update to INTERVIEW
        JobApplication updated = applicationService.updateStatus(app.getId(), "INTERVIEW");
        assertEquals("INTERVIEW", updated.getStatus());
    }
}

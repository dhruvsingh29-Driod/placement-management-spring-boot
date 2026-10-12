package com.dhruv.placementmanagement.service;

import com.dhruv.placementmanagement.entity.JobApplication;
import com.dhruv.placementmanagement.entity.PlacementDrive;
import com.dhruv.placementmanagement.entity.Student;
import com.dhruv.placementmanagement.repository.JobApplicationRepository;
import com.dhruv.placementmanagement.repository.PlacementDriveRepository;
import com.dhruv.placementmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final PlacementDriveRepository driveRepository;

    public JobApplicationService(JobApplicationRepository applicationRepository,
                                 StudentRepository studentRepository,
                                 PlacementDriveRepository driveRepository) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
    }

    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    public List<JobApplication> getApplicationsByStudent(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    public List<JobApplication> getApplicationsByDrive(Long driveId) {
        return applicationRepository.findByPlacementDriveId(driveId);
    }

    public JobApplication apply(Long studentId, Long driveId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new IllegalArgumentException("Placement drive not found with ID: " + driveId));

        // 1. Eligibility Check: CGPA Cutoff
        if (student.getCgpa() != null && student.getCgpa() < drive.getMinCgpa()) {
            throw new IllegalStateException("Student CGPA (" + student.getCgpa() + ") does not satisfy cutoff (" + drive.getMinCgpa() + ")");
        }

        // 2. Eligibility Check: Branch matching
        if (drive.getEligibleBranches() != null && !drive.getEligibleBranches().isEmpty()) {
            List<String> allowed = Arrays.stream(drive.getEligibleBranches().split(","))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .toList();
            String studentBranch = student.getBranch() != null ? student.getBranch().trim().toUpperCase() : "";
            if (!allowed.contains(studentBranch)) {
                throw new IllegalStateException("Student branch (" + student.getBranch() + ") is not eligible for this drive (" + drive.getEligibleBranches() + ")");
            }
        }

        // 3. Duplicate Application Guard
        if (applicationRepository.existsByStudentIdAndPlacementDriveId(studentId, driveId)) {
            throw new IllegalStateException("Student has already applied for this placement drive.");
        }

        JobApplication app = new JobApplication(student, drive, "APPLIED");
        return applicationRepository.save(app);
    }

    public JobApplication updateStatus(Long applicationId, String newStatus) {
        return applicationRepository.findById(applicationId)
                .map(app -> {
                    app.setStatus(newStatus.toUpperCase());
                    return applicationRepository.save(app);
                })
                .orElseThrow(() -> new IllegalArgumentException("Application not found with ID: " + applicationId));
    }
}

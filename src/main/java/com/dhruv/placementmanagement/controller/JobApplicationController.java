package com.dhruv.placementmanagement.controller;

import com.dhruv.placementmanagement.entity.JobApplication;
import com.dhruv.placementmanagement.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Job Application Controller", description = "Endpoints for managing student applications and recruitment workflow transitions")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    public JobApplicationController(JobApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @Operation(summary = "Get all student job applications")
    public List<JobApplication> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get application history for a specific student")
    public List<JobApplication> getApplicationsByStudent(@PathVariable Long studentId) {
        return applicationService.getApplicationsByStudent(studentId);
    }

    @GetMapping("/drive/{driveId}")
    @Operation(summary = "Get candidate applications for a specific drive")
    public List<JobApplication> getApplicationsByDrive(@PathVariable Long driveId) {
        return applicationService.getApplicationsByDrive(driveId);
    }

    @PostMapping
    @Operation(summary = "Submit a student application to a placement drive with eligibility verification")
    public ResponseEntity<JobApplication> apply(@RequestBody Map<String, Long> payload) {
        Long studentId = payload.get("studentId");
        Long driveId = payload.get("driveId");
        if (studentId == null || driveId == null) {
            return ResponseEntity.badRequest().build();
        }
        JobApplication application = applicationService.apply(studentId, driveId);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update candidate recruitment stage (APPLIED, SHORTLISTED, INTERVIEW, SELECTED, REJECTED)")
    public ResponseEntity<JobApplication> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(applicationService.updateStatus(id, status));
    }
}

package com.dhruv.placementmanagement.repository;

import com.dhruv.placementmanagement.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStudentId(Long studentId);
    List<JobApplication> findByPlacementDriveId(Long driveId);
    boolean existsByStudentIdAndPlacementDriveId(Long studentId, Long driveId);
    long countByStatus(String status);
}

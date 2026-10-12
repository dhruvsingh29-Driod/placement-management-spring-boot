package com.dhruv.placementmanagement.repository;

import com.dhruv.placementmanagement.entity.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlacementDriveRepository extends JpaRepository<PlacementDrive, Long> {
    List<PlacementDrive> findByCompanyId(Long companyId);
    List<PlacementDrive> findByStatus(String status);
    List<PlacementDrive> findByMinCgpaLessThanEqualAndStatus(Double cgpa, String status);
}

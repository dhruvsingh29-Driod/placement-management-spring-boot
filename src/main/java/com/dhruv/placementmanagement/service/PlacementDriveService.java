package com.dhruv.placementmanagement.service;

import com.dhruv.placementmanagement.entity.PlacementDrive;
import com.dhruv.placementmanagement.repository.PlacementDriveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlacementDriveService {

    private final PlacementDriveRepository driveRepository;

    public PlacementDriveService(PlacementDriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public List<PlacementDrive> getAllDrives() {
        return driveRepository.findAll();
    }

    public List<PlacementDrive> getActiveDrives() {
        return driveRepository.findByStatus("OPEN");
    }

    public Optional<PlacementDrive> getDriveById(Long id) {
        return driveRepository.findById(id);
    }

    public List<PlacementDrive> getDrivesByCompany(Long companyId) {
        return driveRepository.findByCompanyId(companyId);
    }

    public List<PlacementDrive> getDrivesEligibleForCgpa(Double cgpa) {
        return driveRepository.findByMinCgpaLessThanEqualAndStatus(cgpa, "OPEN");
    }

    public PlacementDrive createDrive(PlacementDrive drive) {
        return driveRepository.save(drive);
    }

    public PlacementDrive updateDriveStatus(Long id, String status) {
        return driveRepository.findById(id)
                .map(d -> {
                    d.setStatus(status);
                    return driveRepository.save(d);
                })
                .orElseThrow(() -> new IllegalArgumentException("Drive not found with ID: " + id));
    }
}

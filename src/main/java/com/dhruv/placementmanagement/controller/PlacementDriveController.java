package com.dhruv.placementmanagement.controller;

import com.dhruv.placementmanagement.entity.PlacementDrive;
import com.dhruv.placementmanagement.service.PlacementDriveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
@Tag(name = "Placement Drive Controller", description = "Endpoints for publishing and querying campus recruitment drives")
public class PlacementDriveController {

    private final PlacementDriveService driveService;

    public PlacementDriveController(PlacementDriveService driveService) {
        this.driveService = driveService;
    }

    @GetMapping
    @Operation(summary = "Get all placement drives")
    public List<PlacementDrive> getAllDrives() {
        return driveService.getAllDrives();
    }

    @GetMapping("/active")
    @Operation(summary = "Get currently active/open placement drives")
    public List<PlacementDrive> getActiveDrives() {
        return driveService.getActiveDrives();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get placement drive details by ID")
    public ResponseEntity<PlacementDrive> getDriveById(@PathVariable Long id) {
        return driveService.getDriveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get drives posted by a specific company")
    public List<PlacementDrive> getDrivesByCompany(@PathVariable Long companyId) {
        return driveService.getDrivesByCompany(companyId);
    }

    @GetMapping("/eligible")
    @Operation(summary = "Query drives that a student qualifies for based on CGPA")
    public List<PlacementDrive> getEligibleDrives(@RequestParam Double cgpa) {
        return driveService.getDrivesEligibleForCgpa(cgpa);
    }

    @PostMapping
    @Operation(summary = "Publish a new campus placement drive")
    public ResponseEntity<PlacementDrive> createDrive(@RequestBody PlacementDrive drive) {
        PlacementDrive created = driveService.createDrive(drive);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update placement drive status (OPEN/CLOSED)")
    public ResponseEntity<PlacementDrive> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(driveService.updateDriveStatus(id, status));
    }
}

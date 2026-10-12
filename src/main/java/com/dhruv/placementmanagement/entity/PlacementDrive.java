package com.dhruv.placementmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "placement_drives")
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String jobTitle;

    @Column(length = 2000)
    private String jobDescription;

    @Column(nullable = false)
    private Double minCgpa;

    @Column(nullable = false)
    private String eligibleBranches; // Comma-separated: "CSE,IT,ECE"

    private Double packageLpa;

    private LocalDate deadline;

    @Column(nullable = false)
    private String status = "OPEN";

    public PlacementDrive() {}

    public PlacementDrive(Company company, String jobTitle, String jobDescription,
                          Double minCgpa, String eligibleBranches, Double packageLpa,
                          LocalDate deadline, String status) {
        this.company = company;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.minCgpa = minCgpa;
        this.eligibleBranches = eligibleBranches;
        this.packageLpa = packageLpa;
        this.deadline = deadline;
        this.status = status != null ? status : "OPEN";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public Double getMinCgpa() { return minCgpa; }
    public void setMinCgpa(Double minCgpa) { this.minCgpa = minCgpa; }

    public String getEligibleBranches() { return eligibleBranches; }
    public void setEligibleBranches(String eligibleBranches) { this.eligibleBranches = eligibleBranches; }

    public Double getPackageLpa() { return packageLpa; }
    public void setPackageLpa(Double packageLpa) { this.packageLpa = packageLpa; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

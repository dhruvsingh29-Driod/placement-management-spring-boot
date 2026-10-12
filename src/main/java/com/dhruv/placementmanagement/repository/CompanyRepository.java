package com.dhruv.placementmanagement.repository;

import com.dhruv.placementmanagement.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    List<Company> findByIndustry(String industry);
    boolean existsByEmail(String email);
}

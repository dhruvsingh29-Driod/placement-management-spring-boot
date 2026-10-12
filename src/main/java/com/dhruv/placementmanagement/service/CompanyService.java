package com.dhruv.placementmanagement.service;

import com.dhruv.placementmanagement.entity.Company;
import com.dhruv.placementmanagement.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Optional<Company> getCompanyByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    public List<Company> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry);
    }

    public Company saveCompany(Company company) {
        if (company.getId() == null && companyRepository.existsByEmail(company.getEmail())) {
            throw new IllegalArgumentException("Company with email " + company.getEmail() + " already exists.");
        }
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company updated) {
        return companyRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setIndustry(updated.getIndustry());
                    existing.setLocation(updated.getLocation());
                    return companyRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + id));
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new IllegalArgumentException("Company not found with ID: " + id);
        }
        companyRepository.deleteById(id);
    }
}

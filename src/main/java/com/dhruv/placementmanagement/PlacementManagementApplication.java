package com.dhruv.placementmanagement;

import com.dhruv.placementmanagement.entity.Company;
import com.dhruv.placementmanagement.entity.PlacementDrive;
import com.dhruv.placementmanagement.entity.Student;
import com.dhruv.placementmanagement.repository.CompanyRepository;
import com.dhruv.placementmanagement.repository.PlacementDriveRepository;
import com.dhruv.placementmanagement.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PlacementManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlacementManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(StudentRepository studentRepository,
                                          CompanyRepository companyRepository,
                                          PlacementDriveRepository driveRepository) {
        return args -> {
            if (studentRepository.count() == 0) {
                Student s1 = new Student("Ananya Rao", "ananya@vit.edu", "CSE", 8.7, "2026");
                Student s2 = new Student("Karthik Iyer", "karthik@vit.edu", "ECE", 7.9, "2026");
                studentRepository.save(s1);
                studentRepository.save(s2);

                Company c1 = new Company("TCS", "hr@tcs.com", "IT Services", "Mumbai");
                Company c2 = new Company("Infosys", "hr@infosys.com", "Consulting", "Bangalore");
                companyRepository.save(c1);
                companyRepository.save(c2);

                PlacementDrive d1 = new PlacementDrive(c1, "Software Engineer Trainee",
                        "Entry-level engineering role for CSE and IT graduates with training program.",
                        7.0, "CSE,IT", 3.6, LocalDate.now().plusDays(30), "OPEN");

                PlacementDrive d2 = new PlacementDrive(c2, "Systems Engineer",
                        "Full-stack and cloud infrastructure operations role.",
                        6.5, "CSE,ECE,IT", 3.6, LocalDate.now().plusDays(45), "OPEN");

                driveRepository.save(d1);
                driveRepository.save(d2);
                System.out.println("✅ Placement Database seeded with initial students, companies, and drives.");
            }
        };
    }
}

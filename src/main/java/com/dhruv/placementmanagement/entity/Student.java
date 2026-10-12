package com.dhruv.placementmanagement.entity;
import jakarta.persistence.*;

    @Entity
    @Table(name = "students")
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(unique = true, nullable = false)
        private String email;

        private String branch;

        private Double cgpa;

        private String graduationYear;

        public Student() {
        }

        public Student(String name, String email, String branch,
                       Double cgpa, String graduationYear) {
            this.name = name;
            this.email = email;
            this.branch = branch;
            this.cgpa = cgpa;
            this.graduationYear = graduationYear;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public Double getCgpa() {
            return cgpa;
        }

        public void setCgpa(Double cgpa) {
            this.cgpa = cgpa;
        }

        public String getGraduationYear() {
            return graduationYear;
        }

        public void setGraduationYear(String graduationYear) {
            this.graduationYear = graduationYear;
        }
    }


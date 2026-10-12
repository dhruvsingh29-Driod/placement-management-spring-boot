# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-10-13
### Added
- Enterprise layered architecture with Student, Company, PlacementDrive, and JobApplication entities.
- Spring Data JPA repository interfaces with custom query methods.
- Transactional Service layer implementing server-side eligibility verification and duplicate application guards.
- RESTful controller APIs with SpringDoc OpenAPI 3 / Swagger UI interactive documentation at `/swagger-ui/index.html`.
- In-memory H2 database profile (`application.properties`) for zero-setup execution and tests.
- Production MySQL 8.0 configuration profile (`application-mysql.properties`).
- Automated test suite with JUnit 5, Mockito unit tests, and Spring Boot integration tests.
- Multi-stage Dockerfile and docker-compose orchestration.
- Global exception handling returning standardized RFC-compliant error payloads.

Citronix
Table of Contents
Introduction
Project Context
Features
Technical Requirements
Constraints
Technical Skills and Objectives
Cross-Functional Skills
Project Setup
Evaluation Modalities
Deliverables
Demonstration and Presentation Guidelines
Database Design
Introduction
Citronix is a Java-based farm management application designed to help farmers efficiently manage lemon production, harvesting, and sales. The application provides tools to oversee farms, fields, trees, harvests, and sales, aiming to optimize productivity tracking based on tree age and seasonal yields.

The project adheres to a layered architecture, focusing on scalable and maintainable development practices, and is built with industry-standard tools and technologies.

Project Context
The Citronix project involves:

Managing farms, fields, trees, harvests, and sales data.
Automating calculations such as tree productivity and farm revenue.
Enforcing business rules like tree planting seasons and field size constraints.
Features
1. Farm Management
Create, update, delete, and view farm information (name, location, size, creation date).
Perform multi-criteria searches using CriteriaBuilder.
2. Field Management
Associate fields with farms and define their sizes.
Ensure field size constraints:
Total field area must be less than the farm area.
Minimum size: 0.1 hectares (1,000 m²).
Maximum size: 50% of the farm area.
Limit each farm to a maximum of 10 fields.
3. Tree Management
Track trees by planting date, age, and associated field.
Automatically calculate tree ages.
Productivity based on age:
Young Trees (<3 years): 2.5 kg/season.
Mature Trees (3–10 years): 12 kg/season.
Old Trees (>10 years): 20 kg/season.
Enforce tree planting season (March to May).
4. Harvest Management
Record harvests per season (winter, spring, summer, autumn).
Restrict to one harvest per field per season.
Track harvest details such as date and total quantity.
5. Harvest Details
Associate each tree with a specific harvest.
Track the quantity harvested per tree.
6. Sales Management
Record sales with details such as date, unit price, client, and associated harvest.
Calculate revenue: Revenue = Quantity × Unit Price.
Technical Requirements
Technologies Used
Frameworks: Spring Boot
Database: PostgreSQL
Build Tool: Maven
Testing: JUnit, Mockito
Libraries:
Lombok: Simplifies entity management.
MapStruct: Converts between entities and DTOs.
Swagger: API documentation.
Development Tools:
IntelliJ IDEA
Postman
Git and GitHub
Architecture
Layered architecture:
Controller: RESTful APIs.
Service: Business logic.
Repository: Data persistence.
Entity: Database mapping.
Centralized exception handling.
Project Architecture and Value-Added Components
The Citronix project follows a modular and scalable architecture, adhering to best practices for modern software development. This ensures maintainability, reusability, and adherence to industry standards.

1. Service Layer
The service layer acts as the bridge between the controllers and the repositories. It encapsulates business logic and ensures a clean separation of concerns. Key highlights of the service layer include:

Business Logic: Implements constraints like the maximum number of fields in a farm, validation of harvest seasons, and productivity calculations for trees based on age.
Centralized Exception Handling: Ensures consistent error responses across the application.
Reusable Services: Abstracts core operations like CRUD while applying business rules, making the application easy to maintain and extend.
2. Repository Layer
The repository layer is designed for efficient database interactions using Spring Data JPA.

Custom Queries: Implements custom JPA queries for complex use cases like fetching farms by location or calculating total revenue for specific seasons.
Criteria API: Enables advanced search functionalities such as multi-criteria filtering for farms.
Encapsulation: Hides the complexity of database interactions from higher layers.
3. Data Transfer Objects (DTOs)
DTOs are used to transfer data between the client and the application.

Optimized Payloads: Reduces the size of API responses by only including the necessary fields.
Separation of Concerns: Ensures the entities remain decoupled from API models, improving flexibility.
Validation Integration: DTOs are validated using Bean Validation annotations, ensuring data integrity at the API boundary.
4. ViewModels (VMs)
ViewModels provide a refined representation of entities for specific use cases like analytics and reporting.

Custom Formats: Offers aggregated data like farm productivity, revenue reports, and harvest summaries.
Transformation Layer: Built using MapStruct for seamless conversion from entities to ViewModels.
5. Data Validation
Validation is a cornerstone of the project, ensuring that only valid data enters the system.

Spring Bean Validation: Used for field-level validation such as minimum farm size, valid tree planting periods, and single harvest constraints per season.
Custom Validators: Implements project-specific rules like tree density limits and productive age range validation for trees.
6. Database Versioning with Liquibase
The project employs Liquibase for managing database schema changes in a controlled and traceable manner.

Change Logs: Maintains a history of all database changes, including table creations, constraints, and seed data.
Rollback Support: Enables easy reversion of changes if needed.
Collaboration: Facilitates team collaboration by ensuring that all developers work with the same database schema version.
Sample Changes:
Table definitions for Farm, Field, Tree, Harvest, and Sales.
Constraints such as unique keys, foreign keys, and check constraints for business rules.
Seed data for initial testing and demonstrations.
Added Value of the Architecture
This layered architecture with well-defined components offers the following benefits:

Scalability: Each layer can evolve independently, making the project adaptable to future requirements.
Maintainability: Modular design allows easy debugging, testing, and updates.
Performance Optimization: DTOs and repositories ensure optimized database interactions and lightweight API responses.
Data Integrity: Validation and Liquibase ensure consistent and reliable data across the application.
Developer Productivity: Tools like MapStruct and Liquibase reduce boilerplate code and improve development speed.
This structured approach sets the Citronix project apart as a robust and professional solution for lemon farm management.

Constraints
Field Area:
Minimum: 0.1 hectares.
Maximum: 50% of farm area.
Field Count: Max 10 fields per farm.
Tree Density: Max 100 trees/hectare.
Tree Productivity: Max 20 years.
Planting Season: Trees can only be planted from March to May.
Harvesting Rules:
One harvest per season per field.
Trees cannot be included in multiple harvests within the same season.
Technical Skills and Objectives
Skills to Develop
Configure and set up a development environment.
Implement business logic and data access components.
Create and manage relational databases.
Perform unit testing with coverage using JUnit and Mockito.
Competencies
Layered application design.
Data validation and constraints handling.
Exception management and logging.
Cross-Functional Skills
Efficient task planning and collaboration.
Clear and concise presentation of results.
Effective professional interaction in a team environment.
Project Setup
Prerequisites
Install Java JDK 17.
Set up PostgreSQL database.
Clone the project repository:
git clone https://github.com/MOHAMMED-MIESSAL/Citronix.git'
Maven Verify that Apache Maven is installed and configured. You can download Maven from here. Ensure it is added to your system PATH.
PostgreSQL Database Install PostgreSQL on your system. Create a database named citronix_db. Example configuration: Database Name: citronix_db Username: postgres Password: postgres JDBC URL: jdbc:postgresql://localhost:5432/citronix_db

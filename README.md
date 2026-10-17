# Student Grading System

A backend application built using **Java, Spring Boot, and PostgreSQL** to manage student information and support academic grading operations.

## Overview

The Student Grading System is a REST API-based application designed to organize student records, subjects, and marks. It uses Spring Data JPA to interact with a PostgreSQL database.

## Technologies Used

- **Language:** Java
- **Backend Framework:** Spring Boot
- **Database:** PostgreSQL
- **ORM:** Hibernate / Spring Data JPA
- **Build Tool:** Maven
- **API Testing:** Postman
- **Version Control:** Git and GitHub

## Features

- Manage student records.
- Organize subject information.
- Store and manage student marks.
- Provide REST API endpoints for application data.
- Persist data in PostgreSQL using JPA repositories.

## Project Structure

```text
Student-Grading-System/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/sumanth/student_grading_system/
│       └── resources/
│           └── application.properties
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Database Configuration

The application uses PostgreSQL. Create a database named `student_grading_system` and configure your local database credentials using environment variables.

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_grading_system
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
server.port=8081
```

Set `DB_PASSWORD` in your environment before starting the application. Never commit your actual database password to GitHub.

## How to Run

1. Install Java and PostgreSQL.
2. Create the `student_grading_system` database.
3. Set the `DB_PASSWORD` environment variable to your PostgreSQL password.
4. Open a terminal in the project directory.
5. Run the application:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

6. Access the student API at:

   `http://localhost:8081/students`

## API

The application provides REST endpoints for student, subject, mark, and grade-related operations. Refer to the corresponding controller classes for the exact routes and supported HTTP methods.

## Future Improvements

- Add a frontend dashboard.
- Implement validation and consistent error handling.
- Add automated tests.
- Improve grading reports and documentation.

## Author

**Pedakanti Sumanth**

GitHub: [@PedakantiSumanth](https://github.com/PedakantiSumanth)

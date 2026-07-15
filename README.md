# 🏢 StaffHub - Employee Management System

**StaffHub** is a modern Employee Management REST API built with **Spring Boot 3** and **Java 21**. It provides a scalable backend for managing employees, departments, and authentication, making it suitable for HR management systems, enterprise dashboards, or mobile applications.

The project follows a clean layered architecture that separates controllers, services, repositories, and domain models, making it easy to maintain, test, and extend.

---

## 🚀 Features

- Employee management
- Department management
- RESTful API design
- Layered architecture
- Spring Security integration
- JWT Authentication *(planned)*
- Google OAuth2 Authentication *(planned)*
- H2 & PostgreSQL database support
- Docker support *(planned)*

---

## 🛠️ Technologies Used

- **Java 21** - Core programming language
- **Spring Boot 3** - Backend application framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database abstraction layer
- **H2 Database** - Development database
- **PostgreSQL** - Production database
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management & build tool

---

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── naviroq/
│   │           └── staffhub/
│   │               ├── controller/    # Handles incoming HTTP requests (REST APIs)
│   │               ├── service/       # Contains business logic
│   │               ├── repository/    # Database access layer
│   │               └── model/         # JPA entities and domain models
│   └── resources/
│       ├── application.properties     # Application configuration
│       ├── static/                    # Static assets
│       └── templates/                 # HTML templates (if using Thymeleaf)
└── test/                              # Unit and integration tests
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 21 or later
- Maven *(or use the included Maven Wrapper)*

### Clone the Repository

```bash
git clone git@github.com:Yusuf-R/StaffHub.git
cd StaffHub
```

### Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

or using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## 🗺️ Roadmap

- [x] Spring Boot project setup
- [x] Lombok integration
- [ ] Configure H2 Database & JPA
- [ ] Employee CRUD APIs
- [ ] Department CRUD APIs
- [ ] Spring Security integration
- [ ] JWT Authentication
- [ ] Google OAuth2 Authentication
- [ ] PostgreSQL support
- [ ] Docker containerization
- [ ] Unit testing
- [ ] Integration testing

---

## 📌 Project Status

🚧 **Currently under active development.**

New features and improvements are being added incrementally as the project evolves.
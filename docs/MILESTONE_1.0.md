# 🏢 StaffHub - The Connected Workplace

StaffHub is an enterprise-grade employee management system built with Spring Boot. It provides a robust foundation for managing employee profiles, user authentication, and onboarding workflows with a clean separation of concerns and atomic transaction safety.

---

## 🚀 Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Framework** | Spring Boot 3.x |
| **ORM** | Spring Data JPA (Hibernate) |
| **Database** | PostgreSQL |
| **Security** | Spring Security + JWT |
| **API** | RESTful |
| **Build Tool** | Maven |
| **Java Version** | 17+ |

---

## 📁 Project Structure

```
src/main/java/com/naviroq/staffhub/
├── common/                  # Shared utilities
│   ├── constants/           # Standard enums (DepartmentStandard, PositionStandard)
│   ├── entity/               # BaseEntity (audit fields: id, createdAt, updatedAt)
│   ├── enums/                # Shared enums (Gender, RoleCode, UserStatus, etc.)
│   ├── exception/            # Custom exceptions & GlobalExceptionHandler
│   └── seeder/                # DatabaseSeeder (populates Departments & Positions on startup)
│
├── config/                  # Spring configuration (Security, JWT, OpenAPI)
│
├── organization/            # HR & Organization domain
│   ├── controller/           # Department, Position, Employee, Onboarding endpoints
│   ├── domain/                # DTOs, Commands, Entities
│   │   ├── department/        # Department DTOs & Commands
│   │   ├── employee/           # Employee DTOs, Commands, Onboarding DTOs
│   │   ├── entity/              # Employee, Department, Position entities
│   │   └── position/           # Position DTOs & Commands
│   ├── mapper/                # MapStruct / Manual mappers
│   ├── repository/            # JPA repositories
│   └── service/                # Business logic & utilities
│       ├── impl/               # Service implementations
│       └── util/                # EmployeeCodeGeneratorService
│
├── identity/                # Authentication & Authorization domain
│   ├── controller/           # User endpoints, Auth endpoints
│   ├── domain/                # User DTOs, Commands, Entities
│   ├── mapper/                # User mapper
│   ├── repository/            # UserRepository
│   ├── security/               # JWT filter, UserDetailsService
│   └── service/                # UserService, AuthService
│
├── hr/                       # HR domain (Leave Management)
├── engagement/               # Engagement domain (Announcements, Events)
├── platform/                 # Platform utilities (Audit, Notifications)
└── StaffHubApplication.java  # Main entry point
```

---

## 🧱 Core Architecture

### Layered Design

```
┌─────────────────────────────────────────────────────────────────┐
│                     HTTP Layer (Controllers)                    │
│  - Accept JSON requests                                         │
│  - Validate DTOs (@Valid)                                       │
│  - Return Response DTOs                                         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Mapping Layer (Mappers)                      │
│  - DTO ↔ Command ↔ Entity                                        │
│  - Manual mapping or MapStruct                                  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                Service Layer (Business Logic)                   │
│  - Orchestrate operations                                        │
│  - Apply @Transactional for atomicity                            │
│  - Encapsulate business rules                                    │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                 Persistence Layer (Repositories)                 │
│  - CRUD operations                                                │
│  - Custom queries                                                 │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Database (PostgreSQL)                       │
│  - employees, users, departments, positions                     │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🎯 Core Features (Built)

### 1. Department & Position Management
- Standardized Departments and Positions are **seeded automatically** on application startup.
- **Idempotent seeder** — runs safely every time without duplicating data.
- Departments have a unique `code` (e.g., `SE`, `HR`, `FIN`).

### 2. Employee Management
- Full CRUD operations for employee profiles.
- **Auto-generated employee codes** in format: `{DepartmentCode}-{YYMMDD}-{6DigitHex}`.
- Each employee belongs to a **Department** and a **Position**.
- Optional **manager** reference (self-referencing `@ManyToOne`).

### 3. User Management
- Separate `User` entity for **authentication credentials**.
- Password hashing using **BCrypt**.
- User status (`ACTIVE`, `PENDING`, `SUSPENDED`).
- Role-based access (`EMPLOYEE`, `ADMIN`, `SUPER_ADMIN`).

### 4. Onboarding Flow (Atomic)
- Combines Employee + User creation in a **single transaction**.
- If either fails, **both roll back** (`@Transactional`).
- Returns combined `Employee + User` response.

---

## 🔄 Data Flow: Onboarding a New Employee

```
Client sends POST /api/v1/staff-hub/onboarding
    ↓
OnboardStaffRequest (DTO) validated
    ↓
OnboardStaffMapper maps DTO → OnboardStaffCommand
    ↓
OnboardStaffServiceImpl.onboardStaff() (with @Transactional)
    │
    ├── 4a. EmployeeService.createEmployee()
    │       ├── Fetch Department by ID
    │       ├── Fetch Position by ID
    │       ├── Generate Employee Code (e.g., "SE-260724-6E054E")
    │       ├── Build & Save Employee entity
    │       └── Return Employee (with generated ID)
    │
    └── 4b. UserService.createUser()
            ├── Fetch Employee by ID
            ├── Encode password (BCrypt)
            ├── Build & Save User entity (linked to Employee)
            └── Return User
    ↓
UserMapper maps User → UserResponseDto
    ↓
Client receives 201 Created with Employee + User data
```

---

## 📝 API Endpoints

### Onboarding

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/staff-hub/onboarding` | Create Employee + User in one atomic transaction |

### Employees

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/staff-hub/employees` | Create Employee only |
| `GET` | `/api/v1/staff-hub/employees` | List all Employees |
| `GET` | `/api/v1/staff-hub/employees/{id}` | Get Employee by ID |
| `PUT` | `/api/v1/staff-hub/employees/{id}` | Update Employee |
| `DELETE` | `/api/v1/staff-hub/employees/{id}` | Delete Employee |

### Users

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/users` | Create User only (requires Employee ID) |
| `GET` | `/api/v1/users` | List all Users |
| `GET` | `/api/v1/users/{id}` | Get User by ID |
| `PUT` | `/api/v1/users/{id}` | Update User |
| `DELETE` | `/api/v1/users/{id}` | Delete User |

---

## 📦 Example Request: Onboarding

**Endpoint:** `POST /api/v1/staff-hub/onboarding`

**Request Body:**

```json
{
  "firstName": "Sarah",
  "lastName": "Johnson",
  "gender": "FEMALE",
  "dateOfBirth": "1990-05-15",
  "hireDate": "2025-01-01",
  "phone": "+1-555-123-4567",
  "address": "123 Main St, New York",
  "bio": "Senior backend engineer",
  "profilePictureUrl": "https://example.com/photos/sarah.jpg",
  "employmentType": "FULL_TIME",
  "status": "PROBATION",
  "departmentId": "f8e6430c-2a08-4ce1-bc67-fb94c61d44e7",
  "positionId": "9fdaae4a-e28b-473c-945a-0070d6b2cd01",
  "managerId": null,
  "username": "sarah.johnson",
  "email": "sarah.johnson@company.com",
  "roleCode": "EMPLOYEE",
  "password": "SecurePass123!"
}
```

**Response (201 Created):**

```json
{
  "id": "3f603f6e-0bb1-49d9-9c24-0e6cc544a7fc",
  "username": "sarah.johnson",
  "email": "sarah.johnson@company.com",
  "roleCode": "EMPLOYEE",
  "status": "ACTIVE",
  "employee": {
    "id": "d1775725-8dda-4b8f-bd3a-e4c9faf3ba52",
    "employeeCode": "SE-260724-6E054E",
    "fullName": "Sarah Johnson"
  }
}
```

---

## 🗄️ Database Schema

### Core Tables

| Table | Purpose | Key Columns |
| :--- | :--- | :--- |
| `departments` | Department lookup | `id` (PK), `code` (UNIQUE), `name` |
| `positions` | Position lookup | `id` (PK), `title`, `description` |
| `employees` | Employee profiles | `id` (PK), `employee_code` (UNIQUE), `department_id` (FK), `position_id` (FK), `manager_id` (FK self) |
| `users` | Authentication credentials | `id` (PK), `employee_id` (FK UNIQUE), `username`, `email`, `password_hash`, `role`, `status` |

### Relationships

```
┌─────────────────┐             ┌─────────────────┐
│      User        │             │     Employee      │
├─────────────────┤             ├─────────────────┤
│ id (PK)          │◄────────────│ id (PK)           │
│ username         │  One-to-One  │ employee_code     │
│ email            │             │ first_name        │
│ password_hash    │             │ last_name         │
│ role             │             │ department_id     │──┐
│ status           │             │ position_id       │──┼──┐
│ employee_id (FK) │────────────►│ manager_id        │  │  │
└─────────────────┘             └─────────────────┘  │  │
                                                        │  │
                                     ┌──────────────────┘  │
                                     │                      │
                               ┌─────▼─────┐        ┌───────▼────────┐
                               │ Department │        │    Position     │
                               ├───────────┤        ├────────────────┤
                               │ id (PK)    │        │ id (PK)         │
                               │ code       │        │ title           │
                               │ name       │        │ description     │
                               └───────────┘        └────────────────┘
```

---

## 🚦 How to Run the Project

### Prerequisites
- Java 17+
- PostgreSQL 14+
- Maven 3.8+

### 1. Clone the Repository

```bash
git clone https://github.com/naviroq/staffhub.git
cd staffhub
```

### 2. Configure Database

Create a PostgreSQL database:

```sql
CREATE DATABASE staff_hub;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/staff_hub
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Build and Run

```bash
./mvnw clean install
./mvnw spring-boot:run
```

### 4. Seed Data

The `DatabaseSeeder` will automatically run on startup and populate:
- 10 Departments (HR, IT, SE, DevOps, FIN, MKT, SALES, SUPPORT, RND, ADMIN)
- 10 Positions (Backend Developer, Frontend Engineer, etc.)

### 5. Test with Postman

Import the API collection (to be added) or use the example requests above.

---

## 🔑 Key Design Decisions

| Decision | Rationale |
| :--- | :--- |
| Separate `User` & `Employee` | Authentication data is isolated from HR data. More secure and flexible. |
| DTOs for HTTP validation | Validation happens at the boundary. Services receive validated Commands. |
| Commands for Services | Services don't depend on HTTP DTOs. Loose coupling. |
| Mappers between layers | Each layer has its own data shape. No leaking between layers. |
| Interfaces for Services | Controllers depend on abstractions, not concrete implementations. |
| `@Transactional` on onboarding | Employee + User creation is atomic. All or nothing. |
| Auto-generated employee codes | Consistent, unique, and human-readable (`SE-260724-6E054E`). |
| Data Seeder | Self-healing system. Always has standard Departments and Positions. |
| UUIDs for IDs | Globally unique, secure (not guessable like auto-increment). |
| `BaseEntity` with timestamps | Every table has `createdAt` and `updatedAt` (enterprise standard). |

---

## 🧠 What We Learned

| Concept | How We Applied It |
| :--- | :--- |
| DTO vs Command vs Entity | DTO for HTTP, Command for Service, Entity for Database. |
| Mapper Pattern | Convert between layers without leaking dependencies. |
| Service Orchestration | One service coordinates multiple sub-services. |
| Atomic Transactions | `@Transactional` ensures all-or-nothing execution. |
| Code Generation | Auto-generate employee codes with department prefix + timestamp + random. |
| Data Seeding | Populate lookup tables on startup (idempotent). |
| Validation | `@Valid` + custom validation annotations. |
| Global Exception Handling | `@ControllerAdvice` centralizes error responses. |

---

## 🛠️ Next Steps (Roadmap)

- [ ] **Leave Management** — Submit, approve, track leave balances
- [ ] **Announcements** — Company-wide posts with read receipts
- [ ] **Employee Search** — Filter by department, status, name
- [ ] **Audit Logging** — Track who changed what (Spring Data Envers)
- [ ] **Email Notifications** — Send welcome emails, leave approvals (JavaMailSender)
- [ ] **Profile Updates** — Self-service updates with admin approval flow

---
## 👨‍💻 Author

**NaviRoq** - [@naviroq](https://github.com/naviroq)
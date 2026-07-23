# StaffHub Database Entity Relationship Diagram (ERD)

> Version: MVP 1.0
>
> This document defines the logical database architecture of StaffHub.
> It is the primary reference for all JPA entities, relationships, and
> future database migrations.

---

# High-Level Domains

```text
                   ┌─────────────────────────────┐
                   │         IDENTITY            │
                   │ Users • Roles              │
                   └──────────────┬──────────────┘
                                  │
                                  │
                   ┌──────────────▼──────────────┐
                   │      ORGANIZATION           │
                   │ Employees • Departments     │
                   │ Positions • Employment      │
                   └──────────────┬──────────────┘
                                  │
        ┌─────────────────────────┼────────────────────────┐
        │                         │                        │
        ▼                         ▼                        ▼
   HR DOMAIN               ENGAGEMENT              PLATFORM
 Leave Requests          Events/Galleries      Audit/Notifications
```

---

# 1. Identity

## users

| Column | Type | Notes |
|---------|------|------|
| id | UUID | PK |
| username | VARCHAR | Unique |
| email | VARCHAR | Unique |
| password | VARCHAR | BCrypt |
| status | ENUM(UserStatus) | ACTIVE, LOCKED... |
| last_login | TIMESTAMP | Nullable |
| created_at | TIMESTAMP | BaseEntity |
| updated_at | TIMESTAMP | BaseEntity |

Relationship

```
User
 *
 |
 |
 *
Role
```

---

## roles

| Column | Type |
|---------|------|
| id | UUID |
| code | VARCHAR UNIQUE |
| display_name | VARCHAR |
| description | TEXT |
| created_at | TIMESTAMP |
| updated_at | TIMESTAMP |

Examples

```
EMPLOYEE
MANAGER
ADMIN
SUPER_ADMIN
```

---

## user_roles

Bridge table

| Column |
|----------|
| user_id |
| role_id |

Composite Primary Key

```
(user_id, role_id)
```

---

# 2. Organization

## employees

| Column |
|----------|
| id |
| employee_code |
| first_name |
| last_name |
| phone |
| address |
| bio |
| gender |
| hire_date |
| profile_picture |
| status |
| created_at |
| updated_at |

---

## departments

| Column |
|----------|
| id |
| name |
| description |
| parent_department_id |
| head_employee_id |

Self Reference

```
Department

└── Department

    └── Department
```

---

## positions

| Column |
|----------|
| id |
| title |
| description |

Examples

```
Software Engineer

HR Officer

Operations Manager
```

---

## employment

Employment represents an employee's assignment.

Instead of placing department and position directly inside Employee,
StaffHub models employment as a separate entity.

This supports:

- promotions
- transfers
- historical records
- future reporting

Columns

| Column |
|----------|
| id |
| employee_id |
| department_id |
| position_id |
| manager_employee_id |
| employment_type |
| employment_status |
| start_date |
| end_date |

Relationships

```
Employee

1

|

|

*

Employment

* -------- 1 Department

*

|

1 Position
```

---

# 3. Human Resources

## leave_types

Examples

```
Annual Leave

Sick Leave

Study Leave

Compassionate Leave
```

---

## leave_requests

| Column |
|----------|
| id |
| employee_id |
| leave_type_id |
| start_date |
| end_date |
| reason |
| status |
| approved_by |
| approved_at |

Relationship

```
Employee

1

|

*

LeaveRequest

* ------ 1 LeaveType
```

---

## leave_balances

Tracks remaining leave per employee per year.

```
Employee

1

|

*

LeaveBalance
```

---

## profile_update_requests

Stores approval workflow for sensitive profile updates.

```
Employee

1

|

*

ProfileUpdateRequest
```

---

# 4. Engagement

## announcements

```
Announcement

created_by → User

target_department → Department (optional)
```

---

## announcement_reads

Tracks who has viewed an announcement.

```
Announcement

*

|

|

*

User
```

---

## events

Company milestones.

```
End of Year Party

Hackathon

Promotion Ceremony
```

---

## galleries

Each event may own multiple galleries.

```
Event

1

|

*

Gallery
```

---

## photos

```
Gallery

1

|

*

Photo
```

---

## photo_tags

```
Photo

*

|

|

*

Employee
```

---

# 5. Platform

## notifications

```
User

1

|

*

Notification
```

---

## audit_logs

Every important action.

```
User

1

|

*

AuditLog
```

Examples

```
LOGIN

LOGOUT

PROFILE_UPDATED

LEAVE_APPROVED

ROLE_ASSIGNED
```

---

## attachments

Reusable uploaded files.

May later become polymorphic.

---

# Complete Relationship Overview

```
                  +----------------+
                  |     User       |
                  +----------------+
                         *
                         |
                         |
                         *
                  +----------------+
                  |     Role       |
                  +----------------+

                         |
                         |
                         |
                         ▼

                  +----------------+
                  |   Employee     |
                  +----------------+
                         |
                         |
                         *
                  +----------------+
                  | Employment     |
                  +----------------+
                    *           *
                    |           |
                    |           |
                    ▼           ▼
             Department     Position

Employee
   │
   ├──────────── Leave Requests
   │
   ├──────────── Leave Balances
   │
   ├──────────── Profile Update Requests
   │
   └──────────── Photo Tags

Announcement ─────► Department

Announcement ─────► User (Author)

Event

└── Gallery

    └── Photo

        └── PhotoTag

User

├── Notification

└── Audit Log
```

---

# MVP Build Order

## Phase 1

- BaseEntity
- Role
- User
- JWT Authentication

---

## Phase 2

- Department
- Position
- Employee
- Employment

---

## Phase 3

- Leave Types
- Leave Requests
- Leave Balance

---

## Phase 4

- Announcements
- Events
- Galleries
- Photos

---

## Phase 5

- Notifications
- Audit Logs
- Dashboard Aggregation API
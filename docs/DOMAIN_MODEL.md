# Domain Model

> **StaffHub**
>
> **The Connected Workplace**
>
> This document defines the business language of StaffHub.
>
> It describes the core business entities, their responsibilities, lifecycles, relationships, and business rules before any database tables, APIs, or source code are created.
>
> Every architectural decision should align with this document.

---

# Domain Philosophy

StaffHub models an organization, not just its data.

Every entity exists because it represents something meaningful within the workplace.

The domain model focuses on people, organizational structure, communication, collaboration, accountability, and operational workflows.

Technology choices (Spring Boot, PostgreSQL, React, etc.) are implementation details.

The business domain comes first.

---

# Business Domains

StaffHub is divided into six core business domains.

```text
Identity & Access
│
├── User
├── Role
└── Authentication

Organization
│
├── Employee
├── Department
├── Position
└── Reporting Structure

Communication
│
├── Announcement
├── Event
├── Company Gallery
└── Photo

Human Resources
│
├── Leave Request
├── Leave Balance
└── Profile Update Request

Infrastructure
│
├── Audit Log
├── Notification
└── Attachment

Dashboard
│
└── Story Aggregation
```

---

# Domain Principles

## Identity is not a Person

A user account represents authentication and authorization.

An employee represents a person employed by the organization.

These are separate concepts.

---

## Departments define structure.

Departments describe how the organization is organized.

Permissions do not come from departments.

Permissions come from roles.

---

## Roles define authority.

An employee may belong to the Engineering department.

Their authority comes from being:

* Employee
* Manager
* Admin
* Super Admin

Department and role should never be confused.

---

## Workflows preserve integrity.

Sensitive operations should follow approval workflows rather than direct updates.

Examples include:

* Salary changes
* Department transfers
* Position changes
* Name corrections

---

## Every important action is traceable.

Critical operations should produce immutable audit records.

Nothing important should happen silently.

---

# Core Entities

---

# User

## Purpose

Represents an authenticated identity within StaffHub.

A User is responsible only for authentication, authorization, and account security.

---

## Responsibilities

* Login
* Password management
* Account status
* Role assignment
* Session ownership

---

## Owns

* Credentials
* Authentication history
* Active sessions

---

## Lifecycle

```text
Created
    ↓
Activated
    ↓
Locked (optional)
    ↓
Disabled
```

---

## Business Rules

* Every User belongs to exactly one Employee.
* A User cannot exist without an Employee.
* Authentication concerns belong here—not in Employee.

---

# Employee

## Purpose

Represents a person employed by the organization.

Employee is the heart of StaffHub.

Nearly every business process begins with an employee.

---

## Responsibilities

* Belongs to a department
* Holds a position
* Reports to a manager
* Owns a user account
* Requests leave
* Appears in company events
* Participates in organizational activities

---

## Owns

* Leave Requests
* Leave Balance
* Profile
* Profile Update Requests
* Profile Picture

---

## Lifecycle

```text
Created
    ↓
Active
    ↓
Inactive
    ↓
Archived
```

---

## Business Rules

* Every Employee belongs to one Department.
* Every Employee holds one Position.
* Every Employee has one User account.
* Every Employee may report to another Employee.
* Employees cannot approve their own requests.

---

# Department

## Purpose

Represents an organizational unit within the company.

Departments organize employees.

They do not define permissions.

---

## Responsibilities

* Group employees
* Define reporting structure
* Organize announcements
* Assign department heads

---

## Owns

* Employees
* Department announcements

---

## Lifecycle

```text
Created
    ↓
Operational
    ↓
Merged
    ↓
Archived
```

---

## Business Rules

* Departments may have parent departments.
* Departments may have child departments.
* Departments may have one department head.

---

# Position

## Purpose

Represents an official job title within the organization.

Positions define responsibilities—not permissions.

---

## Responsibilities

Examples include:

* Backend Engineer
* HR Officer
* Finance Manager
* Product Designer

---

## Business Rules

* Multiple employees may hold the same position.
* Promotions change positions.
* Permissions are controlled by roles.

---

# Announcement

## Purpose

Represents official organizational communication.

Announcements help employees stay informed.

---

## Responsibilities

* Company news
* Department updates
* Important notices
* Policy changes

---

## Lifecycle

```text
Draft
    ↓
Published
    ↓
Archived
```

---

## Business Rules

* Announcements may target the entire organization.
* Announcements may target specific departments.
* Only authorized users may publish announcements.

---

# Event

## Purpose

Represents an organizational activity.

Events strengthen company culture.

Examples include:

* Town Hall
* Team Building
* Promotion Ceremony
* Anniversary Celebration

---

## Owns

* Gallery
* Photos

---

## Business Rules

Past events become part of the company's history.

---

# Company Gallery

## Purpose

Represents collections of photographs attached to organizational events.

The gallery preserves organizational memories.

---

## Responsibilities

* Event albums
* Recognition moments
* Milestones

---

## Business Rules

Every gallery belongs to one Event.

---

# Photo

## Purpose

Represents an image stored within StaffHub.

Photos may include tagged employees.

---

## Responsibilities

* Image metadata
* Employee tags
* Upload information

---

# Leave Request

## Purpose

Represents an employee's request for time away from work.

---

## Lifecycle

```text
Pending
    ↓
Approved
    ↓
Completed

Rejected

Cancelled
```

---

## Business Rules

* Employees create leave requests.
* Managers approve team requests.
* Employees cannot approve their own leave.
* Cancelation is only allowed while pending.

---

# Leave Balance

## Purpose

Tracks available leave entitlement for each employee.

---

## Responsibilities

* Annual leave
* Sick leave
* Used leave
* Remaining leave

---

## Business Rules

Leave balances are adjusted only after approval.

---

# Profile Update Request

## Purpose

Represents requested changes to protected employee information.

---

## Responsibilities

Tracks:

* old value
* new value
* requester
* approver
* timestamps

---

## Business Rules

Protected information is never modified directly.

Approval is required before changes become effective.

---

# Audit Log

## Purpose

Provides a permanent history of significant actions.

---

## Responsibilities

Tracks:

* who
* what
* when
* where
* previous value
* new value

---

## Business Rules

Audit records are immutable.

They cannot be edited or deleted.

---

# Notification

## Purpose

Represents system-generated communication delivered to users.

Examples:

* Leave approved
* Password reset
* New announcement
* Profile request approved

---

# Attachment

## Purpose

Represents uploaded files associated with business entities.

Examples include:

* Documents
* Images
* Supporting files

Attachments are referenced by business entities rather than existing independently.

---

# Relationships

```text
Department
    │
    ├───────────────┐
    │               │
    ▼               ▼
Employee ───────── Position
    │
    ├────────────── User
    │
    ├────────────── Leave Request
    │
    ├────────────── Leave Balance
    │
    ├────────────── Profile Update Request
    │
    └────────────── Audit Log

Department
    │
    └────────────── Announcement

Event
    │
    └────────────── Company Gallery
                        │
                        └────────────── Photo
                                        │
                                        └──────── Employee Tags
```

---

# Aggregate Roots

The primary aggregates within StaffHub are:

* Employee
* Department
* Announcement
* Event
* Leave Request

All supporting entities belong to one of these aggregate roots.

---

# Ubiquitous Language

Throughout the project, the following terms have precise meanings:

| Term                   | Meaning                                         |
| ---------------------- | ----------------------------------------------- |
| User                   | Authentication identity                         |
| Employee               | Person employed by the organization             |
| Role                   | Authorization level                             |
| Department             | Organizational unit                             |
| Position               | Job title                                       |
| Announcement           | Official communication                          |
| Event                  | Organizational activity                         |
| Gallery                | Collection of event photos                      |
| Leave Request          | Request for time away from work                 |
| Leave Balance          | Employee leave entitlement                      |
| Profile Update Request | Approval workflow for protected profile changes |
| Audit Log              | Immutable record of system activity             |

These definitions should remain consistent across documentation, database design, APIs, backend services, frontend components, and business discussions.

---

# Guiding Principle

> **StaffHub models an organization—not just a database.**

Every new entity, workflow, or feature should strengthen the connected workplace by remaining consistent with the business language defined in this document.

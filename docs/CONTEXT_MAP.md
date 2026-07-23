# Context Map

> **StaffHub**
>
> **Strategic Domain Design**
>
> This document defines the major business contexts within StaffHub, their responsibilities, ownership boundaries, and how they collaborate.
>
> The goal is to ensure every business capability has a clear owner and that contexts communicate through well-defined boundaries instead of sharing responsibilities.

---

# Why Contexts Exist

As StaffHub grows, new features should not increase coupling between unrelated parts of the system.

Instead of treating the application as one large collection of tables and services, StaffHub is divided into independent business domains.

Each domain owns its own business rules, data, and responsibilities.

This allows the system to evolve without becoming tightly coupled.

---

# Context Overview

```text
                    StaffHub

        ┌──────────────────────────┐
        │      Identity            │
        └──────────────────────────┘
                    │
                    ▼
        ┌──────────────────────────┐
        │     Organization         │
        └──────────────────────────┘
             │             │
             │             │
             ▼             ▼
      ┌─────────────┐  ┌─────────────┐
      │     HR      │  │ Engagement  │
      └─────────────┘  └─────────────┘
             │             │
             └──────┬──────┘
                    ▼
          ┌──────────────────┐
          │    Platform       │
          └──────────────────┘

                    ▲
                    │
              Dashboard
         (Aggregation Layer)
```

The Dashboard is **not** a business domain.

It aggregates information from multiple contexts to build the employee experience.

---

# Identity Context

## Responsibility

Manage authentication, authorization, and account identity.

---

## Owns

* User
* Role
* Authentication
* Password Credentials
* Account Status

---

## Provides

* Login
* Authentication
* Token validation
* User identity
* Current user information

---

## Depends On

None.

Identity is foundational.

---

# Organization Context

## Responsibility

Model the structure of the organization.

---

## Owns

* Employee
* Department
* Position
* Reporting Structure
* Organization Hierarchy

---

## Provides

* Employee Directory
* Organization Chart
* Reporting Relationships
* Department Membership
* Manager Lookup

---

## Depends On

Identity

(Employee accounts are linked to authenticated users.)

---

# HR Context

## Responsibility

Manage employee workplace operations and employment workflows.

---

## Owns

* Leave Requests
* Leave Balance
* Profile Update Requests

---

## Provides

* Leave Management
* Leave Approval Workflow
* Leave Balance Calculation
* Protected Profile Changes

---

## Depends On

* Organization
* Identity

HR needs employees and authenticated users but does not own them.

---

# Engagement Context

## Responsibility

Manage communication and organizational culture.

---

## Owns

* Announcements
* Events
* Company Gallery
* Photos
* Employee Tags

---

## Provides

* Company News
* Department News
* Event Timeline
* Gallery Browsing
* Workplace Recognition

---

## Depends On

Organization

(Employee information is required for authorship, tagging, and departments.)

---

# Platform Context

## Responsibility

Provide technical capabilities used across the application.

Platform is not part of the business domain.

It supports every business domain.

---

## Owns

* Audit Logs
* Notifications
* File Attachments
* Storage
* Email Delivery
* Background Jobs
* Permission Definitions

---

## Provides

* Audit Trail
* Notification Delivery
* File Storage
* Email Services
* Scheduled Jobs
* Permission Evaluation

---

## Depends On

Every business context may invoke Platform services.

Platform itself does not own business processes.

---

# Dashboard

The Dashboard is an aggregation layer.

It owns no business data.

Instead, it composes information from multiple contexts to build a personalized employee experience.

Example:

```text
Dashboard
│
├── Identity
│     └── Current User
│
├── Organization
│     ├── Team Members
│     └── Reporting Structure
│
├── HR
│     ├── Leave Balance
│     └── Pending Requests
│
├── Engagement
│     ├── Announcements
│     ├── Events
│     └── Galleries
│
└── Platform
      └── Notifications
```

---

# Context Relationships

```text
Identity
    │
    ▼
Organization
    │
    ├──────────────► HR
    │
    └──────────────► Engagement
          │
          ▼
       Platform

Dashboard
    │
    ├── Identity
    ├── Organization
    ├── HR
    ├── Engagement
    └── Platform
```

---

# Ownership Rules

Each piece of information has exactly one owner.

| Information          | Owning Context |
| -------------------- | -------------- |
| User Account         | Identity       |
| Employee Profile     | Organization   |
| Department           | Organization   |
| Position             | Organization   |
| Leave Request        | HR             |
| Leave Balance        | HR             |
| Company Announcement | Engagement     |
| Event                | Engagement     |
| Gallery              | Engagement     |
| Photo                | Engagement     |
| Audit Log            | Platform       |
| Notification         | Platform       |
| Attachment           | Platform       |

No business data should exist in more than one context.

---

# Communication Principles

Contexts collaborate through services and APIs.

Contexts should never modify another context's internal data directly.

Examples:

* HR asks Organization for employee information.
* Engagement asks Organization for department information.
* HR requests Platform to record audit events.
* Engagement requests Platform to deliver notifications.

Responsibilities remain clearly separated.

---

# Guiding Principle

> **High cohesion. Low coupling. Clear ownership.**

Every new feature introduced into StaffHub should belong to one business context.

If a feature appears to belong to multiple contexts, the design should be revisited before implementation.

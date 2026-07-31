# Workflow Engine

> **StaffHub Internal Approval Workflow System**

---

# Vision

The Workflow Engine is the approval system of StaffHub.

Rather than allowing every user to directly modify sensitive data, the Workflow Engine introduces an approval process where changes become **requests** that are reviewed and approved by authorized personnel before they are executed.

This provides:

* Security
* Accountability
* Auditability
* Approval Chains
* Better Governance

The Workflow Engine becomes the single gateway for every operation that requires organizational approval.

---

# Core Philosophy

Instead of

```
Employee
    │
    └── Directly updates database
```

StaffHub follows

```
Employee
      │
      ▼
Submit Workflow Request
      │
      ▼
Workflow Queue
      │
      ▼
Admin / HR Reviews
      │
      ▼
Approve / Reject
      │
      ▼
Business Logic Executes
      │
      ▼
Database Updated
```

No sensitive operation should bypass the Workflow Engine.

---

# What Requires Approval?

Examples include:

## Employee Management

* Onboard Employee
* Transfer Employee
* Promote Employee
* Terminate Employee
* Change Department
* Change Position
* Reactivate Employee

---

## User Profile

Instead of directly editing:

* Email
* Phone Number
* Address
* Marital Status
* Next of Kin
* Emergency Contact

The employee submits a request.

The request is reviewed.

The approved request updates the Employee/User record.

---

## Leave

* Annual Leave
* Sick Leave
* Compassionate Leave
* Study Leave
* Maternity Leave

These are workflow requests.

---

## Organization

Creating or modifying:

* Department
* Position
* Team

can also be approval-based depending on company policy.

---

## Engagement

Possible approval workflows:

* Publish Announcement
* Create Event
* Upload Gallery
* Company Notice

---

# Workflow Lifecycle

```
Draft
    │
    ▼
Submitted
    │
    ▼
Pending Review
    │
 ┌──┴───────────────┐
 │                  │
 ▼                  ▼
Approved         Rejected
 │
 ▼
Business Action Executed
 │
 ▼
Completed
```

Cancelled requests terminate the workflow before review.

---

# Workflow Types

Examples:

```
EMPLOYEE_ONBOARDING

PROFILE_UPDATE

LEAVE_REQUEST

DEPARTMENT_CREATE

POSITION_CREATE

EMPLOYEE_TRANSFER

EMPLOYEE_PROMOTION

EMPLOYEE_TERMINATION

PASSWORD_RESET

ACCOUNT_REACTIVATION
```

Each request belongs to exactly one workflow type.

---

# Workflow Status

```
DRAFT

SUBMITTED

PENDING

APPROVED

REJECTED

CANCELLED

COMPLETED
```

---

# Workflow Entity

Every request is represented by one record.

Example:

```
WorkflowRequest
```

Contains:

* Request Number
* Request Type
* Status
* Payload
* Requested By
* Assigned To
* Created Date
* Reviewed Date
* Decision Comment

---

# Request Payload

Instead of creating dozens of request tables, the workflow stores the request details as JSON.

Example:

```
{
  "firstName": "John",
  "departmentId": "...",
  "positionId": "...",
  "hireDate": "2026-07-30"
}
```

When approved:

```
Payload

↓

Business Service

↓

Real Entity

↓

Database
```

The Workflow Engine stores the request.

Business modules perform the actual work.

---

# Request Number

Every request receives a unique identifier.

Example:

```
REQ-2026-SE-260724-66B96D-000001
```

Structure:

```
REQ

↓

Year

↓

Employee Code

↓

Employee Request Sequence
```

Example:

```
REQ-2026-SE-260724-66B96D-000001

REQ-2026-SE-260724-66B96D-000002

REQ-2026-SE-260724-66B96D-000003
```

Every employee maintains their own sequence.

This makes requests easy to trace and reference.

---

# Approval Levels

```
STAFF

↓

LINE_MANAGER

↓

HR

↓

ADMIN

↓

SUPER_ADMIN
```

Not every request requires every level.

Future workflow policies can define which roles approve each request type.

---

# Responsibilities

The Workflow Engine should only:

* Accept requests
* Store requests
* Route requests
* Track request status
* Record approvals
* Generate request numbers

It **must not** contain business logic.

Business logic remains inside the corresponding modules.

Example:

```
Workflow

↓

Approve Onboarding

↓

Organization Module

↓

EmployeeService

↓

Create Employee
```

The workflow orchestrates; domain services execute.

---

# Current Implementation Progress

## Completed

* Workflow module structure
* WorkflowRequest entity
* WorkflowRequestRepository
* Workflow enums
* Workflow service skeleton
* Request Number Generator
* Per-employee request numbering design

---

## In Progress

* Submit Workflow Request
* Workflow Mapper
* DTOs
* Controller endpoints

---

## Remaining

### Submission

* Submit request
* Validate request
* Generate request number
* Store payload

---

### Review

* Approve request
* Reject request
* Cancel request

---

### Queries

* My Requests
* Pending Requests
* Assigned Requests
* Request Details
* Request History

---

### Execution

Execute the actual business operation after approval.

Examples:

* Create Employee
* Update Employee
* Create Department
* Approve Leave
* Update User Profile

---

### Notifications

* Request Submitted
* Request Approved
* Request Rejected
* Request Cancelled

---

### Audit

Track:

* Who submitted
* Who reviewed
* Decision time
* Comments
* Complete approval history

---

# Long-Term Goal

The Workflow Engine should become the approval backbone of StaffHub.

Every sensitive organizational action should pass through a consistent, traceable, auditable workflow before affecting business data.

This provides a single, extensible approval mechanism that can support HR, Organization, Identity, Engagement, and future modules without duplicating approval logic.

# Relationships

This document defines every relationship in StaffHub.

---

## Organization

| Source | Relationship | Target | Cardinality |
|---------|--------------|---------|------------|
| Employee | has many | Employments | 1:N |
| Department | has many | Employments | 1:N |
| Position | has many | Employments | 1:N |
| Employee | manages | Employments | 1:N (Manager → Employment) |
| Department | has parent | Department | 1:N |

---

## Identity

| Source | Relationship | Target | Cardinality |
|---------|--------------|---------|------------|
| Employee | has one | User | 1:1 |
| Role | has many | Users | 1:N |

---

## HR

| Source | Relationship | Target | Cardinality |
|---------|--------------|---------|------------|
| Employee | has many | Leave Requests | 1:N |
| Leave Type | has many | Leave Requests | 1:N |
| Employee | has many | Leave Balances | 1:N |
| Leave Type | has many | Leave Balances | 1:N |
| Employee | has many | Profile Update Requests | 1:N |

---

## Engagement

| Source | Relationship | Target | Cardinality |
|---------|--------------|---------|------------|
| Employee | authors | Announcements | 1:N |
| Department | targets | Announcements | 1:N (Optional) |
| Event | has many | Galleries | 1:N |
| Gallery | has many | Photos | 1:N |
| Photo | tags | Employees | M:N |
| Employee | reads | Announcements | M:N |

---

## Platform

| Source | Relationship | Target | Cardinality |
|---------|--------------|---------|------------|
| Employee | receives | Notifications | 1:N |
| Employee | performs | Audit Logs | 1:N |
| Attachment | belongs to | Any Entity | Polymorphic |
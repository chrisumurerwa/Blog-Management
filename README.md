Blog-Management API

Blog Management API is a Spring Boot application that provides a secure backend for managing users, blog posts, and comments.
It implements:

JWT-based Authentication
Role-Based Access Control (RBAC)
User and Admin account management

 Key Features
 User Registration & Login (JWT token-based)
 Role-based Access Control
USER: Can create, edit, or delete their own posts/comments.
ADMIN: Can manage all users, posts, and comments.
 JWT Authentication Filter for securing endpoints
 Postgres Database integration using Spring Data JPA
 
 Clean architecture using layered structure:
Controller – handles API endpoints
Service – contains business logic
Repository – manages data access
Config – handles Spring Security & JWT setup


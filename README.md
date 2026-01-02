# Order Reservation Service

A Java-based backend service for managing orders and reservations with transactional consistency and clear domain boundaries.
Implements RESTful APIs using a layered architecture and relational persistence.

## Short Description
A Spring Boot service that enables creation, retrieval, and reservation of orders.
Built with clean separation of concerns, validation, and database-backed state management.

## Tech Stack
- Language: Java 17
- Frameworks: Spring Boot, Spring Web, Spring Data JPA, Hibernate
- Build Tool: Maven
- Database: PostgreSQL

## Key Features
- REST APIs for order creation and retrieval
- Order reservation with lifecycle state handling
- Persistent storage using JPA and PostgreSQL
- Layered architecture (controller, service, repository)
- Input validation and structured error handling
- Clean, extensible project structure

## API Endpoints

### Create Order

POST /api/orders

Request Body:  
    { "customerName": "John Doe", "product": "Laptop", "quantity": 1 }

Response:  
    201 Created

---

### Get Order by ID

GET /api/orders/{id}

Response:  
    200 OK  
    404 Not Found

---

### Get All Orders (with optional pagination)

GET /api/orders?page=0&size=10

Query Params:  
    page = default 0  
    size = default 10

Response:  
    Paged list of orders

---

### Reserve Order

POST /api/orders/{id}/reserve

Response:  
    200 OK  
    409 Conflict (if already reserved)

## Getting Started

Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL instance

Run Instructions
- Configure database properties in application.properties
- Build the project using `mvn clean install`
- Run the application using `mvn spring-boot:run`
- Service runs on port 8080

## Project Structure

    src/main/java
    └── com.example.orderreservation
        ├── controller    -> REST endpoints
        ├── service       -> Business logic
        ├── repository    -> Data access layer
        ├── entity        -> JPA entities
        └── dto           -> API request/response models

## Why This Project Matters
The service demonstrates core backend engineering principles such as domain-driven layering, RESTful API design, transactional data handling, and maintainable code organization suitable for real-world systems.

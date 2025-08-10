# Goodreads Clone – Spring Boot REST API

A simple **Spring Boot**-based REST API for managing books, authors, and publishers, inspired by Goodreads.
Implements full CRUD operations with proper relational mappings using **Spring Data JPA** and **Hibernate**.

---

## Features

* **Books**: Add, update, delete, view books with linked authors and publishers.
* **Authors**: Manage authors and associate them with multiple books.
* **Publishers**: Manage publishers and associate them with books.
* **Relations**:

  * `Book` ↔ `Author`: Many-to-Many
  * `Book` ↔ `Publisher`: Many-to-One
* Input validation with HTTP status codes (`404`, `400`, `204`).
* JPA repositories for database interaction.
* Clear separation of **Model**, **Repository**, and **Service** layers.

---

## Tech Stack

* **Java 11+**
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **H2 / MySQL** (configurable)
* **Jackson** for JSON serialization

---

## Project Structure

```
src/main/java/com/example/goodreads/
│
├── model/
│   ├── Author.java
│   ├── Book.java
│   └── Publisher.java
│
├── repository/
│   ├── AuthorRepository.java / AuthorJpaRepository.java
│   ├── BookRepository.java / BookJpaRepository.java
│   ├── PublisherRepository.java / PublisherJpaRepository.java
│
├── service/
│   ├── AuthorJpaService.java
│   ├── BookJpaService.java
│   └── PublisherJpaService.java
```

---

## API Endpoints (Examples)

### **Books**

```
GET    /books               → Get all books
GET    /books/{id}          → Get book by ID
POST   /books               → Add new book
PUT    /books/{id}          → Update book
DELETE /books/{id}          → Delete book
GET    /books/{id}/authors  → Get authors of a book
GET    /books/{id}/publisher→ Get publisher of a book
```

### **Authors**

```
GET    /authors
GET    /authors/{id}
POST   /authors
PUT    /authors/{id}
DELETE /authors/{id}
```

### **Publishers**

```
GET    /publishers
GET    /publishers/{id}
POST   /publishers
PUT    /publishers/{id}
DELETE /publishers/{id}
```

---

## Setup & Run

1. **Clone repository**

```bash
git clone <repo-url>
cd goodreads
```

2. **Configure DB** in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:goodreads
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

3. **Build & Run**

```bash
mvn spring-boot:run
```

4. **Access API**

```
http://localhost:8080
```

5. **H2 Console** (if enabled)

```
http://localhost:8080/h2-console
```

---

## Notes

* **Error Handling**: Returns `404` when resource not found, `400` for invalid input, and `204` for successful deletion without content.
* **Entity Relationships**: Managed using `@ManyToOne`, `@ManyToMany`, and join tables for author–book mapping.
* **Extensibility**: New fields and relations can be added easily by extending models and repositories.

---

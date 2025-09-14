# Spring Boot MySQL CRUD API

A comprehensive REST API built with Spring Boot that provides CRUD operations for Users and Posts with role-based security, global exception handling, and Swagger documentation.

## 🚀 Features

- **CRUD Operations** for Users and Posts
- **Role-based Security** with HTTP Basic Authentication
- **MySQL Database** integration with JPA/Hibernate
- **Global Exception Handling** for consistent error responses
- **Swagger/OpenAPI Documentation** for API exploration
- **Custom Response DTOs** with standardized API responses
- **Input Validation** with Bean Validation
- **Security Annotations** for method-level authorization

## 🛠️ Technology Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** (HTTP Basic Authentication)
- **Spring Data JPA**
- **MySQL** Database
- **Swagger/OpenAPI 3** for API documentation
- **Maven** for dependency management
- **Bean Validation** for input validation

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## ⚙️ Setup & Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd mysql-crud-api
```

### 2. Database Setup
Create a MySQL database:
```sql
CREATE DATABASE employees_db;
```

### 3. Configuration
Update `application.properties` or `application.yml`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/employees_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Security Configuration
spring.security.user.name=admin
spring.security.user.password=admin123
spring.security.user.roles=ADMIN

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### 4. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📊 Database Schema

### Users Table
- `id` (Long) - Primary Key
- `name` (String) - User's full name
- `role` (String) - User's role (ADMIN/USER)
- `email` (String) - User's email address
- `age` (Integer) - User's age
- Additional audit fields (created_date, updated_date)

### Posts Table
- `id` (Long) - Primary Key
- `title` (String) - Post title
- `content` (String) - Post content
- `user_id` (Long) - Foreign Key referencing Users
- Additional audit fields (created_date, updated_date)

## 🔐 Security & Roles

The application uses HTTP Basic Authentication with role-based access control:

### Roles:
- **ADMIN**: Full access to all operations
- **USER**: Limited access to create posts and view public content

### Security Rules:
- **User Management**: Only ADMIN can create, update, delete users
- **Post Creation**: Both USER and ADMIN can create posts
- **Post Updates/Deletion**: Only post owner or ADMIN can modify/delete posts
- **Public Endpoints**: Anyone can view posts and individual post details

## 📚 API Endpoints

### 🧑‍💼 User Management

#### Get All Users
```http
GET /users
Authorization: Basic admin:admin123
```
**Response**: List of all users with pagination headers

#### Get User by ID
```http
GET /users/{id}
Authorization: Basic admin:admin123
```

#### Create User
```http
POST /users
Authorization: Basic admin:admin123
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30
}
```

#### Update User
```http
PUT /users/{id}
Authorization: Basic admin:admin123
Content-Type: application/json

{
  "name": "John Smith",
  "email": "john.smith@example.com",
  "age": 31
}
```

#### Delete User
```http
DELETE /users/{id}
Authorization: Basic admin:admin123
```

### 📝 Post Management

#### Create Post
```http
POST /posts/user/{userId}
Authorization: Basic user:password
Content-Type: application/json

{
  "title": "My First Post",
  "content": "This is the content of my first post."
}
```

#### Get Posts by User
```http
GET /posts/user/{userId}
```
*Public endpoint - no authentication required*

#### Get Post by ID
```http
GET /posts/{postId}
```
*Public endpoint - no authentication required*

#### Update Post
```http
PUT /posts/{postId}
Authorization: Basic user:password (owner) or admin:admin123
Content-Type: application/json

{
  "title": "Updated Post Title",
  "content": "Updated post content."
}
```

#### Delete Post
```http
DELETE /posts/{postId}
Authorization: Basic user:password (owner) or admin:admin123
```

## 📖 API Documentation

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
Get the OpenAPI specification at:
```
http://localhost:8080/api-docs
```

## 🔍 Response Format

All API responses follow a consistent format using `ApiResponseDTO`:

```json
{
  "statusCode": 200,
  "message": "Operation completed successfully",
  "data": {
    // Actual response data
  }
}
```

### Success Responses:
- `200 OK` - Successful retrieval/update
- `201 Created` - Successful creation
- `204 No Content` - Successful deletion

### Error Responses:
- `400 Bad Request` - Validation errors
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

## ⚠️ Error Handling

The application includes global exception handling that provides:
- Consistent error response format
- Detailed validation error messages
- Proper HTTP status codes
- Security-aware error responses (no sensitive information leakage)

## 🧪 Testing

### Manual Testing
Use the Swagger UI for interactive testing or tools like Postman/curl.

### Authentication for Testing:
```bash
# Admin access
curl -u admin:admin123 http://localhost:8080/users

# User access  
curl -u user:password http://localhost:8080/posts/user/1
```

## 🚦 Custom Headers

The API includes custom headers for enhanced functionality:
- `X-Total-Count` - Total number of items (in list responses)
- `X-App-Version` - Application version
- `X-Request-ID` - Unique request identifier
- `X-Operation` - Type of operation performed
- `Location` - Resource location (in creation responses)

## 📦 Project Structure

```
src/main/java/com/example/mysql_crud/
├── controller/          # REST Controllers
│   ├── UserController.java
│   └── PostController.java
├── service/            # Business Logic Layer
├── dto/               # Data Transfer Objects
├── entity/            # JPA Entities
├── exception          # Global Exceptions
├── logging            # Global Logging
├── mapper             # Mappers to handle dto & entity
├── repository/        # Data Access Layer
├── security/          # Security Configuration
└── constants/         # Application Constants
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Email: your-email@example.com

---

**Happy Coding! 🎉**
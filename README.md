# Travel Agency App - Backend

This project is a backend system designed to support the administration and operation of a travel agency. Built on a microservices architecture, it facilitates efficient management of tours, bookings, user profiles, and notifications.

---

### **Features**

1. **Microservice Architecture**:

    - Each service has its own dedicated database for data isolation and security.
    - Supports both synchronous (e.g., REST API calls) and asynchronous (e.g., Kafka messaging) communication between services.
2. **User Management:**
    
    - User registration and authentication.
    - Role-based access control (ADMIN, GUEST).
    - Assign and validate roles for users.
3. **Tour Management:**
    
    - Retrieve and manage available tours.
    - Handle tour availability and seat bookings.
4. **Booking System:**
    
    - Create, view, and update bookings.
    - Automatically update tour availability based on bookings.
    - Integration with notification service.
5. **Notification Service:**
    
    - Real-time notifications for booking actions (e.g., booking created, booking updated, etc.).
    - Kafka-based messaging integration.
    - Currently, notifications are stored as records in the `tour_notification_service_db` database, providing a centralized repository for booking-related events.
6. **Swagger Integration:**
    
    - Comprehensive API documentation using Swagger.
7. **Role-Based Authorization:**
    
    - Specific endpoints for `ADMIN` and `GUEST` users.
    - JWT-based authentication with roles.

---

### **Technologies Used**

- **Programming Language:** Java 21
- **Key Technologies:**
    - Spring Boot
    - Spring Security
    - Spring Data JPA
    - Spring Web
    - OpenFeign
    - Spring Cloud
    - JWT
- **Database:**
    - PostgreSQL
- **Messaging:**
    - Kafka
- **Other Tools:**
    - Eureka Discovery Server
    - Docker for containerization
    - Swagger for API documentation
    - Lombok

---

### **Modules**

1. **User Service**
    
    - Handles user authentication, role assignment.
    - Supports updating user profiles (e.g., username, email, password, etc.).
    - JWT-based token generation and validation.
2. **Tour Service**
    
    - Manages available tours.
3. **Booking Service**
    
    - Facilitates tour bookings with real-time seat availability updates.
    - Sends event logs to Kafka topics for notification services.
4. **Notification Service**
    
    - Processes Kafka events for bookings.
5. **API Gateway**
    
    - Unified entry point for all services.
    - Handles routing, authentication.
6. **API Gateway Mobile**
    - Represents similar functionality to the `API Gateway` but with restricted access to the User Service, allowing only authentication and registration operations for mobile clients.
7. **Discovery Server**
    - Centralized service registry for service discovery.
    - Ensures seamless communication between microservices. 
---

### **Prerequisites**

- **Java 21** installed.
- **Docker** and **Docker Compose** installed.
- **PostgreSQL** client (optional for database verification).
- **Kafka** and **Zookeeper** (integrated into Docker Compose).
- **Maven**

---

### **Setup Instructions**

1. **Clone the Repository:**
    
    ```bash
    git clone https://github.com/9jer/travel-agency-app.git
    cd travel-agency-app
    ```
    
2. **Build the Project:**
    
    - Build the project using Maven:
        
        ```bash
        mvn clean install
        ```
        
3. **Run the Application with Docker Compose:**
    
    - Navigate to the root directory and start all services:
        
        ```bash
        docker-compose up -d
        ```
        
4. **Access the Application:**
    
    - **Eureka Discovery Server:** `http://localhost:8761`
    - **API Gateway:** `http://localhost:8080`
    - **API Gateway Mobile:** `http://localhost:8084`
    - **Swagger Documentation:** `http://localhost:8080/swagger-ui.html`
    - **Kafka UI:** `http://localhost:8090`

---

### **Overview of Selected Endpoints**

#### **Auth/User Service**

| Method | Endpoint                  | Access | Description                  |
| ------ | ------------------------- | ------ | ---------------------------- |
| POST   | `/api/v1/users`           | Public | Register a new user.         |
| GET    | `/api/v1/users/{id}`      | ADMIN  | Retrieve user details by ID. |
| PATCH  | `/api/v1/users/{id}/role` | ADMIN  | Assign role to a user.       |

#### **Tour Service**

|Method|Endpoint|Access|Description|
|---|---|---|---|
|GET|`/api/v1/tours`|ADMIN|List all available tours.|
|GET|`/api/v1/tours/{id}`|ADMIN|Retrieve tour details by ID.|

#### **Booking Service**

|Method|Endpoint|Access|Description|
|---|---|---|---|
|POST|`/api/v1/bookings`|GUEST, ADMIN|Create a new booking.|
|GET|`/api/v1/bookings`|ADMIN|Retrieve all bookings.|
|PATCH|`/api/v1/bookings/{id}/status`|ADMIN|Update booking status.|

---

### **Environment Variables**

|Variable Name|Default Value|Description|
|---|---|---|
|`SPRING_PROFILES_ACTIVE`|`docker`|Active Spring profile.|
|`SPRING_DATASOURCE_URL`|`jdbc:postgresql://postgres-tour:5432`|PostgreSQL connection URL.|
|`KAFKA_BOOTSTRAP_SERVERS`|`kafka:9092`|Kafka bootstrap server URL.|
|`JWT_SECRET`|`your_secret_key`|Secret key for JWT token signing.|

---

### **Testing**

- **Postman Collection:**  A Postman collection with all API endpoints is available [here](https://drive.google.com/file/d/1n8fDquDBNMtKP1ZYze1I8sdLr4PVto_V/view?usp=sharing).
    
---

## Contact

- Email: vladimir.stxsevich@gmail.com

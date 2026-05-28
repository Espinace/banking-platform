# 🏦 Banking Platform

A backend banking platform built with **Java 21** and **Spring Boot**, designed to simulate core financial operations such as account management, transactions, and transfers.

This project focuses on backend engineering best practices, including RESTful API design, layered architecture, authentication and authorization, containerization, automated testing, and scalable application structure.

> ⚠️ This project is actively under development. Features and documentation are continuously evolving.

---

## 🚀 Tech Stack

| Layer            | Technology              |
| ---------------- | ----------------------- |
| Language         | Java 21                 |
| Framework        | Spring Boot 3           |
| Security         | Spring Security · JWT   |
| Database         | PostgreSQL              |
| Containerization | Docker · Docker Compose |
| Build Tool       | Maven                   |
| Testing          | JUnit 5 · Mockito       |
| API Docs         | Swagger / OpenAPI       |

---

## ✨ Features

* [x] Project structure and Docker setup
* [x] JWT authentication and authorization
* [ ] Account management (create, retrieve, update)
* [ ] Transaction processing (deposits, withdrawals)
* [ ] Fund transfers between accounts
* [ ] Transaction history and balance queries
* [ ] Input validation and exception handling
* [ ] Role-based access control
* [ ] Unit and integration tests
* [ ] API documentation with Swagger UI
* [ ] CI/CD pipeline with GitHub Actions

---

## 🏗️ Architecture

This project follows a layered architecture with clear separation of concerns, aiming for scalability, maintainability, and clean backend design.

```text
Client
   ↓
REST API
   ↓
Controller Layer
   ↓
Service Layer
   ↓
Repository Layer
   ↓
PostgreSQL
```

### Project Structure

```text
src/
└── main/
    └── java/
        └── com/espinace/bankingplatform/
            ├── controller/     # REST endpoints
            ├── service/        # Business logic
            ├── repository/     # Data access layer
            ├── model/          # Domain entities
            ├── dto/            # Data Transfer Objects
            ├── security/       # JWT & authentication
            └── config/         # Application configurations
```

The application is being designed following backend engineering and enterprise application best practices, with focus on clean architecture principles and scalable backend services.

---

## 🔐 Security

The platform is being developed with security best practices in mind, including:

* JWT-based authentication
* Stateless authorization
* Input validation
* Secure API design principles
* Spring Security integration

---

## ⚙️ Getting Started

### Prerequisites

* Java 21+
* Docker and Docker Compose

### Running Locally

```bash
# Clone the repository
git clone https://github.com/Espinace/banking-platform.git

# Navigate to the project
cd banking-platform

# Start PostgreSQL container
docker-compose up -d

# Run the application
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

## 📘 API Documentation

Once the application is running, Swagger UI will be available at:

```text
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 📋 API Endpoints

> Full API documentation is available via Swagger UI.

| Method | Endpoint                          | Description         |
| ------ | --------------------------------- | ------------------- |
| `POST` | `/api/auth/register`              | Register new user   |
| `POST` | `/api/auth/login`                 | Authenticate user   |
| `POST` | `/api/accounts`                   | Create account      |
| `GET`  | `/api/accounts/{id}`              | Get account details |
| `POST` | `/api/transactions/deposit`       | Deposit funds       |
| `POST` | `/api/transactions/withdraw`      | Withdraw funds      |
| `POST` | `/api/transfers`                  | Transfer funds      |
| `GET`  | `/api/accounts/{id}/transactions` | Transaction history |

---

## 🛣️ Roadmap

Planned improvements for future versions:

* Advanced authentication flows
* Refresh token support
* Account ownership validation
* Transaction audit logging
* Observability and monitoring
* API rate limiting
* Test coverage improvements
* Deployment pipeline automation

---

## 🤝 Contributing

Contributions, suggestions, and feature requests are welcome.

Feel free to open issues or submit pull requests.

---

## 👨‍💻 Author

**Bruno Espinace**
Backend Engineer | Java & Spring Boot | Banking Systems

📍 São Paulo, Brazil
🌎 Open to remote international opportunities

* LinkedIn: https://www.linkedin.com/in/bruno-espinace
* GitHub: https://github.com/Espinace

---

## 📄 License

This project is licensed under the MIT License.

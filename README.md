# 🏦 Banking Platform

A backend banking platform built with **Java 21** and **Spring Boot**, designed to simulate core financial operations such as account management, transactions, and transfers. Built with production-grade practices including RESTful API design, containerization, and automated testing.

> ⚠️ This project is actively under development. Features and documentation are being updated regularly.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Database | PostgreSQL |
| Containerization | Docker / Docker Compose |
| Build Tool | Maven |
| Testing | JUnit 5 · Mockito |
| API Docs | Swagger / OpenAPI |

---

## ✨ Features

- [x] Project structure and Docker setup
- [ ] Account management (create, retrieve, update)
- [ ] Transaction processing (deposits, withdrawals)
- [ ] Fund transfers between accounts
- [ ] Transaction history and balance queries
- [ ] Input validation and error handling
- [ ] Unit and integration tests
- [ ] API documentation with Swagger UI
- [ ] CI/CD pipeline with GitHub Actions

---

## 🏗️ Architecture

This project follows a **layered architecture** with clear separation of concerns:

```
src/
└── main/
    └── java/
        └── com/espinace/bankingplatform/
            ├── controller/     # REST endpoints
            ├── service/        # Business logic
            ├── repository/     # Data access layer
            ├── model/          # Domain entities
            └── dto/            # Data Transfer Objects
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 21+
- Docker and Docker Compose

### Running locally

```bash
# Clone the repository
git clone https://github.com/Espinace/banking-platform.git
cd banking-platform

# Start the database
docker-compose up -d

# Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

### API Documentation

Once the application is running, access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 📋 API Endpoints

> Full documentation available via Swagger UI after running the application.

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/accounts` | Create a new account |
| `GET` | `/api/accounts/{id}` | Get account details |
| `POST` | `/api/transactions/deposit` | Deposit funds |
| `POST` | `/api/transactions/withdraw` | Withdraw funds |
| `POST` | `/api/transfers` | Transfer between accounts |
| `GET` | `/api/accounts/{id}/transactions` | Get transaction history |

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome! Feel free to check the [issues page](https://github.com/Espinace/banking-platform/issues).

---

## 👨‍💻 Author

**Bruno Espinace** — Backend Java Developer  
📍 São Paulo, Brazil | Open to remote opportunities worldwide

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/bruno-espinace)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Espinace)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

# Banking System API

A comprehensive RESTful API for simulating real banking operations, built with Spring Boot and MySQL. This system provides secure account management, transaction processing, and financial services.

## Overview

The Banking System API is a robust backend application that replicates core banking functionalities. Built with modern Spring Boot architecture, it demonstrates best practices in building secure, scalable financial applications with features like JWT authentication, email notifications, and PDF statement generation.

## Features

### Account Management
- **User Registration & Authentication**: Secure account creation with Spring Security and JWT tokens
- **Account Inquiry**: Retrieve detailed account information
- **Name Enquiry**: Verify account holder details
- **Profile Management**: Update user information and preferences

### Transaction Operations
- **Credit/Deposit**: Add funds to accounts with transaction tracking
- **Debit/Withdrawal**: Withdraw funds with balance validation
- **Fund Transfer**: Seamless inter-account money transfers
- **Transaction History**: Complete audit trail of all operations

### Additional Features
- **Email Notifications**: Automated transaction alerts and account updates
- **PDF Statements**: Generate and download account statements
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Security**: JWT-based authentication and authorization
- **Data Validation**: Comprehensive input validation and error handling

## Technologies Used

- **Backend Framework:** Spring Boot 3.1.5
- **Language:** Java 21
- **Database:** MySQL
- **Security:** Spring Security with JWT (jjwt 0.12.3)
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven 3.9.5
- **API Documentation:** SpringDoc OpenAPI 2.2.0
- **Email Service:** Spring Boot Mail
- **PDF Generation:** iText 5.5.13
- **Development Tools:** Lombok, Spring Boot DevTools

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- SMTP server (for email notifications)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/Banking-System-API.git
cd Banking-System-API
```

2. **Create MySQL database**
```bash
mysql -u root -p
CREATE DATABASE banking_system;
EXIT;
```

3. **Configure application properties**

Create or update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/banking_system
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080

# JWT Configuration
jwt.secret=your_jwt_secret_key_at_least_256_bits_long
jwt.expiration=86400000

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Spec**: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Create a new account
- `POST /api/auth/login` - User login and receive JWT token

### Account Operations
- `GET /api/account/{accountNumber}` - Get account details
- `GET /api/account/name-enquiry/{accountNumber}` - Verify account holder name
- `PUT /api/account/update` - Update account information

### Transactions
- `POST /api/transaction/credit` - Deposit funds
- `POST /api/transaction/debit` - Withdraw funds
- `POST /api/transaction/transfer` - Transfer between accounts
- `GET /api/transaction/history/{accountNumber}` - Transaction history
- `GET /api/transaction/statement/{accountNumber}` - Generate PDF statement

## Usage Examples

### Register a New Account
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "SecurePassword123",
    "phoneNumber": "+1234567890",
    "address": "123 Main St"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "1234567890",
    "password": "SecurePassword123"
  }'
```

### Transfer Funds
```bash
curl -X POST http://localhost:8080/api/transaction/transfer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "fromAccount": "1234567890",
    "toAccount": "0987654321",
    "amount": 1000.00,
    "description": "Payment for services"
  }'
```

### Java Client Example
```java
RestTemplate restTemplate = new RestTemplate();
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.setBearerAuth(jwtToken);

// Transfer request
TransferRequest request = TransferRequest.builder()
    .fromAccount("1234567890")
    .toAccount("0987654321")
    .amount(new BigDecimal("1000.00"))
    .description("Payment")
    .build();

HttpEntity<TransferRequest> entity = new HttpEntity<>(request, headers);
ResponseEntity<TransferResponse> response = restTemplate.exchange(
    "http://localhost:8080/api/transaction/transfer",
    HttpMethod.POST,
    entity,
    TransferResponse.class
);
```

## Testing

Run the test suite:
```bash
mvn test
```

Run with coverage:
```bash
mvn clean test jacoco:report
```

## Project Structure

```
banking-system/
├── src/
│   ├── main/
│   │   ├── java/com/abbey2u/bankingsystem/
│   │   │   ├── config/          # Security, JWT, Email config
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic
│   │   │   ├── security/        # JWT filters, authentication
│   │   │   └── util/            # Helper classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/       # Email templates
│   └── test/                    # Unit and integration tests
├── pom.xml
└── README.md
```

## Security Features

- **Password Encryption**: BCrypt hashing for secure password storage
- **JWT Authentication**: Stateless token-based authentication
- **Authorization**: Role-based access control
- **Input Validation**: Bean Validation for all API inputs
- **SQL Injection Protection**: JPA/Hibernate parameterized queries
- **CORS Configuration**: Configurable cross-origin policies

## Error Handling

The API uses standard HTTP status codes and returns detailed error messages:

- `200 OK` - Successful operation
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid input or business rule violation
- `401 Unauthorized` - Missing or invalid authentication
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server-side error

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/NewFeature`)
3. Commit your changes (`git commit -m 'Add NewFeature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

### Coding Standards
- Follow Java naming conventions
- Write unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Roadmap

- [ ] Multi-currency support
- [ ] Loan management system
- [ ] Savings account features
- [ ] Fixed deposit accounts
- [ ] Mobile banking API extensions
- [ ] Real-time notifications with WebSocket
- [ ] Enhanced fraud detection
- [ ] Account statements via email
- [ ] Multi-factor authentication (MFA)


## Acknowledgments

- Spring Framework team for excellent documentation
- Banking industry best practices and standards
- Open source community contributors
- RESTful API design principles

---

**Note**: This is a simulation project for educational purposes. Do not use in production without proper security audits and compliance reviews.

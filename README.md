# Chat App 🚀

[![Build Status](https://travis-ci.org/mahmoud-mamdouh-abdullah/chat-system.svg?branch=master)](https://travis-ci.org/mahmoud-mamdouh-abdullah/chat-system)
[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://semver.org)

A modern and scalable chat application built using **Spring Boot**, **MySQL**, **RabbitMQ**, and *
*Elasticsearch**. This enterprise-grade solution implements a microservices architecture, providing robust features for
message persistence, queue-based asynchronous communication, and full-text search capabilities with high performance and
reliability.

---

## Features
- **Real-time Messaging**: Seamless communication via RabbitMQ.
- **Search Capability**: Powered by Elasticsearch, enabling fast and efficient message querying.
- **Database Integration**: Uses MySQL for storing chat-related information.
- **Scalable Design**: Containerized using Docker for deployment and scalability.
- **Spring Boot Architecture**: Modularized and easy to extend.

---

## Tech Stack
1. **Backend**: Spring Boot (Java 17)
2. **Database**: MySQL
3. **Message Queue**: RabbitMQ
4. **Search Engine**: Elasticsearch
5. **Build Tools**: Maven
6. **Containerization**: Docker & Docker Compose

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- Docker and Docker Compose (latest version)
- Java JDK 17 or above
- Maven 3.8+
- Git

### Installation Steps

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/chat-app.git
   cd chat-app
   ```

### Docker Setup
1. Build and run the containers:
   ```bash
   docker-compose up --build
   ```
2. The application will be available at `http://localhost:8080`.

---

## Prerequisites
Ensure the following services are running before starting the application:
1. MySQL database (`3306`)
2. RabbitMQ server (`5672`)
3. Elasticsearch (`9200`)

### Modify the Configuration

If necessary, update connection details in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://db:3306/chat_app
spring.rabbitmq.host=rabbitmq
spring.elasticsearch.uris=http://elasticsearch:9200
```
---

## Running Without Docker
If you prefer running the application without Docker:
1. Start Elasticsearch, RabbitMQ, and MySQL manually.
2. Update the application connection settings in `application.properties`.
3. Run the app using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## Project Structure
```
chat-app
├── src/main/java
│    ├── controller      # REST controllers for API endpoints
│    ├── dto             # Data Transfer Objects
│    ├── entity          # JPA Entity Classes
│    ├── rabbitmq        # RabbitMQ Consumer/Producer
│    ├── repository      # Spring Data Repositories
│    ├── service         # Service Layer for Business Logic
│    └── ChatAppApplication.java # Main Spring Boot Application Entry Point
├── src/main/resources
│    ├── static          # Static Resources (e.g., HTML, CSS, JS)
│    ├── templates       # Thymeleaf Templates
│    └── application.properties # App Configuration
├── Dockerfile           # Docker Image Setup
├── docker-compose.yml   # Container Orchestration
├── pom.xml              # Maven Build Configuration
└── README.md            # Project Documentation
```
---

## API Documentation

### REST Endpoints

#### **Application Endpoints**
| Endpoint                  | Method | Description                         | Request Body               | Response                |
|---------------------------|--------|-------------------------------------|---------------------------|-------------------------|
| `/applications`           | POST   | Create a new application            | `ApplicationRequestDTO`    | `ApplicationResponse`   |
| `/applications`           | GET    | Retrieve all applications           | N/A                       | `ApplicationResponseDTO`     |
| `/applications/{token}`   | GET    | Retrieve an application by token    | N/A                       | `Application`   |

#### **Chat Endpoints**
| Endpoint                                | Method | Description                                 | Request Body | Response                |
|-----------------------------------------|--------|---------------------------------------------|-------------|-------------------------|
| `/applications/{token}/chats`           | POST   | Queue a new chat creation                   | N/A         | `Json Object`           |
| `/applications/{token}/chats`           | GET    | Retrieve all chats for an application token | N/A         | `List<ChatResponseDTO>` |

#### **Message Endpoints**
| Endpoint                                                  | Method | Description                            | Request Body          | Response                  |
|-----------------------------------------------------------|--------|----------------------------------------|-----------------------|---------------------------|
| `/applications/{token}/chats/{chatNumber}/messages`        | POST   | Queue a new message for a chat         | `MessageRequestDTO`   | `Json Object`     |
| `/applications/{token}/chats/{chatNumber}/messages`        | GET    | Retrieve all messages for a chat       | N/A                   | `MessageResponseDTO`           |
| `/applications/{token}/chats/{chatNumber}/messages/search` | GET    | Search messages in a chat by keyword   | N/A                   | `MessageDocumentResponseDTO`           |

---


## License
This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## Contributing
We welcome contributions! Please fork the repository and submit a pull request for review.

---

## Support and Contact

### Technical Support

For technical issues and bugs, please create an issue in
our [Issue Tracker](https://github.com/mahmoud-mamdouh-abdullah/chat-app/issues).

### Contact Information

- **Developer**: Mahmoud Mamdouh
- **Email**: mahmoud.khalil1072@gmail.com
- **LinkedIn**: [Your LinkedIn Profile](https://www.linkedin.com/in/mahmoud-mamdouh-88b72a195/)

Feel free to reach out if you have any questions, suggestions, or collaboration ideas! 🚀
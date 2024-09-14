# Volunteer Platform

Volunteer Platform is a web-based application designed to help connect volunteers with organizations in need. The platform allows users to sign up for volunteer opportunities, and organizations can post events or tasks for volunteers to participate in.

## Features
- Sign up and log in for volunteers and organizations
- Browse volunteer opportunities by category and location
- Apply for volunteer tasks and track your commitments
- Organization management dashboard for posting tasks and events

## Tech Stack

### Backend
- **Java 17**: The application runs on Java 17, leveraging its modern features and performance improvements.
- **Spring Boot**: Used to build a robust, scalable, and easy-to-deploy backend API.
- **Maven**: Project management and build tool to handle dependencies and build automation.

### Frontend
*(In Progress)*

## Getting Started

### Prerequisites
- **Java 17** or later
- **Maven** 3.6+
- **Postgres** 
- **Apache Kafka**
- **Redis**
- **Docker**

### Running Locally

1. **Clone the repository:**

    ```bash
    git clone https://github.com/tertyshnyi/volunteer-platform.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd volunteer-platform
    ```

3. **Navigate to the backend directory:**

    ```bash
    cd backend
    ```

4. **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

5. **Run the Spring Boot application:**

    ```bash
    mvn spring-boot:run
    ```

The backend will be available at `http://localhost:8080`.

## API Documentation
*(If applicable, include information about API documentation or tools like Swagger)*

## Contribution
Contributions are welcome! Please open an issue to discuss major changes before submitting a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

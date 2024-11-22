# Volunteer Platform

Volunteer Platform is a web-based application designed to connect volunteers with organizations in need. Volunteers can browse opportunities and track their commitments, while organizations can manage tasks and events through a dedicated dashboard.

---

## **Features**
- User authentication for volunteers and organizations
- Explore volunteer opportunities by category and location
- Apply for volunteer tasks and track ongoing commitments
- Organization dashboard for posting and managing tasks/events

---

## **Tech Stack**

### **Backend**
- **Java 17**: Leveraging the modern features and improved performance of Java 17.
- **Spring Boot**: Simplifies development and deployment of REST APIs.
- **Maven**: Dependency management and build automation.
- **MySQL**: Relational database for storing application data.
- **Redis**: In-memory data store for caching and session management.
- **Apache Kafka**: Distributed event streaming for asynchronous communication.
- **Docker**: Containerized deployment for consistency across environments.

### **Frontend**
- **React**: Modern frontend framework for building responsive user interfaces.
- **TypeScript**: Enhances JavaScript with static typing for better maintainability.
- **CSS Modules**: Scoped styles for better styling practices.
- **React Router**: Client-side routing for smooth navigation.

---

## **Getting Started**

### **Prerequisites**
Ensure the following tools are installed on your system:
- **Java 17** or later
- **Maven** (3.6+)
- **Node.js** (16+)
- **npm** or **yarn**
- **Docker** and **Docker Compose**

---

## **Running the Project Locally**

1. **Clone the repository:**

    ```bash
    git clone https://github.com/tertyshnyi/volunteer-platform.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd volunteer-platform
    ```
## **Running the Backend**

1. **Navigate to the backend directory:**

    ```bash
    cd backend
    ```

2. **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

3. **Run the Spring Boot application:**

    ```bash
    mvn spring-boot:run
    ```

The backend will be available at `http://localhost:8080`.

## **Running the Frontend**

1. **Navigate to the backend directory:**

    ```bash
    cd frontend
    ```
2. **Install the dependencies:**

   ```bash
   npm install
   ``` 

3. **Start the development server:**

   ```bash
   npm start
   ``` 

The frontend will now be accessible at http://localhost:3000.

## **Running the Project with Docker**

#### **Prerequisites**
Ensure that **Docker** and **Docker Compose** are installed on your system.

#### **Steps to Run**

1. **Build and start the Docker containers:**
   ```bash
   docker-compose up --build
   ```

2. **Access the services:**
   
   Backend API: http://localhost:8080
   
   Frontend: http://localhost:3000
   
   Redis: http://localhost:6379
   
   MySQL: http://localhost:3307

## API Documentation
To view the API documentation, ensure the backend is running and open the Swagger UI at: http://localhost:8080/swagger-ui.html (if Swagger is configured).

## Contribution
Contributions are welcome! Follow these steps to contribute:

1. **Fork the repository.**

2. **Create a new branch:**
   ```bash
   git checkout -b feature-name
   ```

3. **Make your changes and commit:**
   ```bash
   git commit -m "Add feature-name"
   ```

4. **Push to your branch:**
   ```bash
   git push origin feature-name
   ```

5. **Open a pull request on the main repository.**
   
## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

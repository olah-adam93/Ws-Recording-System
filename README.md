# Recording Information Manager

[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

Web application built with **Spring Boot** and **Vue.js** for managing recording information.

---

## Table of Contents

- [Background](#background)
- [Installation](#installation)
- [Usage](#usage)
- [Local Pages](#local-pages)

---

## Background

This application is designed to manage recording information, providing real-time updates using WebSocket communication. It allows users to:

- **Real-Time Updates**: Uses WebSocket to push updates from the backend to the frontend.
- **Status-Based Rendering**:
  - `scheduled` and `reported` recordings are displayed as read-only.
  - `recorded` recordings allow editing of specific fields (sedation, activation, medication).
- **Data-Driven Interactions**: All interactions are driven by the JSON data received from the backend.
- **H2 In-Memory Database**: Uses an embedded H2 database.

---

### WebSocket Endpoints

- **WebSocket Connection**: `ws://localhost:8080/ws`
- **Subscribe to Recordings**: `/topic/recordings`
- **Request Recordings**: `/app/request-recordings`
- **Update Recording**: `/app/update-recording`

---

## Installation

### Prerequisites

Before running the application, ensure you have the following installed:

- **Java SDK (JDK 17)**: [Download JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Node.js and NPM**: [Download Node.js](https://nodejs.org/)

---

## Usage

### Setting Up the H2 Database

The application uses an **H2 in-memory database**. The database is automatically configured with the following properties:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
```

---

### Installing Dependencies

- **Backend**:

  - Open the project in IntelliJ IDEA and run Gradle to install all required dependencies.

- **Frontend**:
  - Open the project in VS Code, navigate to the project folder, and run the following command in the terminal:
    ```bash
    npm install
    ```

### Running the Application

- **Backend**:

  - Run by the main method:

  ```bash
  src/main/java/com/example/recording_app/RecordingAppApplication.java
  ```

- **Frontend**:
  - Navigate to the `frontend` directory and start the development server:
    ```bash
    npm run serve
    ```

## Local Pages

- **Backend**: [http://localhost:8080](http://localhost:8080)
- **Frontend**: [http://localhost:8081](http://localhost:8081)
- **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

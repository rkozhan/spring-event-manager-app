# Spring Event Manager App

This is the backend service for the Event Manager application, built using Spring Boot. The backend provides a comprehensive API for managing users, events, and registrations. It also includes authentication and authorization features using JWT.

This application was created as a graduation project for Coders.Bay.

## Features

- **User Management**: Sign up, login, and manage user profiles.
- **Event Management**: Create, update, delete, and view events.
- **Registration Management**: Register for events, view registrations.
- **Admin Functionality**: Manage events and users with elevated privileges.
- **Authentication**: Secure access using JWT for authentication.
- **Database**: Uses MongoDB for data persistence.

## Planned Features

- **Favorite Events**: Ability to mark events as favorites for quick access.
- **Password Recovery**: Feature to allow users to reset their passwords if forgotten.
- **Image Uploads**: Support for adding images to events for better visual representation.
- **Email Verification**: Require email confirmation when creating an editor account.

## Frontend

The frontend of the Event Manager application is built with Angular. It provides a user-friendly interface for interacting with the backend services.

- **Frontend Repository:** [Event Manager Frontend on GitHub](https://github.com/rkozhan/angular-event-mgr)
- **Live Demo:** [Event Manager Application on GitHub Pages](https://rkozhan.github.io/angular-event-mgr)


## API Documentation

You can access the live API here: [Spring Event Manager API](https://spring-event-manager-app.onrender.com)

For example, you can view events at: [https://spring-event-manager-app.onrender.com/api/v1/events](https://spring-event-manager-app.onrender.com/api/v1/events)

## Test User Credentials

- **Email:** `user@mail.com`
- **Password:** `password`

## Running Locally

### Prerequisites

- Java 17
- Maven
- MongoDB

### Installation

1. **Clone the repository:**:
   ```sh
   git clone https://github.com/rkozhan/spring-event-manager-app.git
   cd spring-event-manager-app

2. **Install dependencies:**:
   ```sh
   mvn install


3. **Run the application:**:
   ```sh
   mvn spring-boot:run


## Project Structure
- **Controllers**: Handle HTTP requests and responses.
- **Services**: Business logic and interaction with repositories.
- **Models**: Data models representing the entities in the application.
- **Repositories**: Data access layer for MongoDB.
- **Security**: JWT provider and configuration for securing endpoints.

## Configuration
The application uses Spring Boot's default configurations. For custom configurations, modify the application.yml file.
Default Port: 8080 (http://localhost:8080/api/v1/).

### Environment Variables
- **JWT_SECRET**: Secret key for signing JWT tokens.
- **MONGODB_URI**: MongoDB connection string.

## API Endpoints

### AuthController: `/auth/*`

- **POST /auth/signup**: Sign up a new user.
- **POST /auth/login**: Authenticate a user and return a JWT.
- **POST /auth/admin**: Create a new admin user.

### EventController: `/api/v1/events/*`
- **`GET /api/v1/events/all`**: Retrieves all events.
- **`POST /api/v1/events`**: Creates a new event.
- **`PUT /api/v1/events`**: Updates an event.
- **`GET /api/v1/events/{id}`**: Retrieves an event by ID.
- **`DELETE /api/v1/events/{id}`**: Deletes an event by ID.
#### Additional Endpoints
- **`GET /api/v1/events`**: Retrieves all upcoming events.
- **`PUT /api/v1/events/{id}`**: Toggles the `isCancelled` status of an event.
- **`GET /api/v1/events/detailed/{id}`**: Retrieves detailed information about an event by ID.
- **`GET /api/v1/events/random`**: Retrieves a random detailed event.

### UserController: `/api/v1/users/*`
- **`GET /api/v1/users`**: Retrieves all users.
- **`GET /api/v1/users/{id}`**: Retrieves a user by ID.
- **`PUT /api/v1/users`**: Updates a user.
- **`DELETE /api/v1/users/{id}`**: Deletes a user.
#### Additional Endpoints
- **`GET /api/v1/users/email/{email}`**: Retrieves a user by email.
- **`GET /api/v1/users/me`**: Retrieves the current user's details based on the JWT token.
- **`GET /api/v1/users/detailed/{id}`**: Retrieves detailed user information by ID.

### RegistrationController: `/api/v1/registrations/*`

- **`GET /api/v1/registrations`**: Retrieves all registrations.
- **`POST /api/v1/registrations`**: Creates a new registration.
- **`GET /api/v1/registrations/{id}`**: Retrieves a registration by ID.
- **`DELETE /api/v1/registrations/{id}`**: Deletes a registration by ID.
- **`DELETE /api/v1/registrations/{eventId}/{userId}`**: Deletes a registration by event ID and user ID.
- **`GET /api/v1/registrations/users/{userId}`**: Retrieves all registrations for a specific user.
- **`GET /api/v1/registrations/events/{eventId}`**: Retrieves all registrations for a specific event.


## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## Acknowledgements

- **Spring Boot**: A framework for building production-ready applications in Java, simplifying the setup and development process.
- **MongoDB**: A NoSQL database that offers flexible schema design, scalability, and performance for storing event and user data.
- **JWT**: JSON Web Tokens used for secure authentication and authorization, enabling stateless communication between client and server.
- **Spring Security**: A powerful and customizable authentication and access control framework for securing Java applications, integrated with Spring Boot for robust security features.
- **Maven**: A build automation and dependency management tool used to manage the project's dependencies and build process.
- **JUnit**: A testing framework for writing and running tests in Java, ensuring the reliability and correctness of the application.
- **Mockito**: A mocking framework used for unit testing in Java, allowing developers to simulate the behavior of complex objects and dependencies.
- **Lombok**: A Java library that helps reduce boilerplate code by generating getters, setters, and other utility methods at compile time.
- **ThreetenBP**: A backport of the Java 8 date and time API, providing a comprehensive date and time library for Java SE 6 and 7.


## Status

This project is currently in **beta** and is still under active development.


killing event-manager-0.0.1-SNAPSHOT.jar:
jps
taskkill /PID <PID> /F

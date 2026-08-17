# Task Tracker

## Description

Task Tracker is a Spring Boot web application that allows users to register, log in, and manage tasks.

Users can create, edit, delete, search, filter, and update task status. Tasks are stored in memory using an ArrayList, so all task data is cleared when the application restarts.

## Features

* User registration, login, and logout
* Create, edit, and delete tasks
* Task priority: Low, Medium, High
* Task status: Pending or Completed
* Search by title or description
* Filter by status and priority
* Pagination with 10 tasks per page
* Form validation
* Error handling with @ControllerAdvice
* SLF4J logging
* Date/time API integration
* OpenWeather API integration
* Responsive layout using CSS Grid and Flexbox

## Technologies

* Java
* Spring Boot
* Spring MVC
* Spring Security
* Thymeleaf
* HTML/CSS
* Jakarta Validation
* Maven
* SLF4J
* OpenWeather API
* In-memory ArrayList

## Project Structure

src/main/
├── java/com/project/tasktracker/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── repository/
│   ├── config/
│   └── exception/
│
└── resources/
    ├── templates/
    ├── static/
    └── application.properties

## Architecture

The application follows a layered MVC structure:


Browser
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
ArrayList<Task>

The Controller handles requests, the Service contains application logic, the Repository stores tasks, and Thymeleaf templates display the user interface.

## Validation and Error Handling

Task fields are validated using annotations such as:

@NotBlank
@NotNull
@FutureOrPresent


The application also uses @ControllerAdvice to handle errors and display clear messages to the user.

## Logging

SLF4J is used to log important events including:

* Successful and failed logins
* Task creation
* API failures

## External APIs

A public date/time API is used to timestamp created tasks.

The OpenWeather API displays current weather information on the dashboard.

The weather API key is provided through an environment variable:

weather.api.key=${OPENWEATHER_API_KEY}

## How to Run

### 1. Set the OpenWeather API key

Linux/macOS:

export OPENWEATHER_API_KEY='your_api_key_here'


### 2. Run the application

Linux/macOS:


./mvnw spring-boot:run

If permission is denied:

chmod +x mvnw


### 3. Open the application

Visit:

http://localhost:8080/login


Register an account, log in, and access the task dashboard.

## Important Note

Tasks and registered users are stored only in memory.

Restarting the application clears all saved tasks and users.

Need to provide your own API key either add 
* export OPENWEATHER_API_KEY= 'your key' (then run application)
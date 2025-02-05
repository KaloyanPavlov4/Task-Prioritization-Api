Task Prioritization API

This project provides an API for managing tasks with priority and completion status. It allows users to create, update, delete, and view tasks, as well as filter them by various parameters such as priority and completion status.
Features Implemented

    Task Management: Create, read, update, and delete tasks.
    Task Filtering: Filter tasks by completion status and priority.
    Pagination & Sorting: Supports pagination and sorting by task priority.
    Validation: Task inputs are validated with Java Bean Validation (e.g., @NotNull, @Valid).
    Sentiment analyis using Stanford Natural Language Processing that adds a sentiment analysis to each task.

Setup Instructions
Prerequisites

    Docker: Ensure you have Docker and Docker Compose installed.
    Java 17+: The project is built with Java 17.

1. Clone the Repository

git clone https://github.com/yourusername/task-prioritization.git
cd task-prioritization

2. Build & Run with Docker Compose

To start the project with Docker Compose, follow these steps:

    Make sure you have Docker Compose installed.
    Run the following command to start the application and PostgreSQL database:

docker-compose up --build

This will build the Docker containers and start the Spring Boot application with the PostgreSQL database.
The app listens to port 8080

3. API Endpoints

The following API endpoints are available:<br/>
## GET /tasks

Fetch a list of tasks with optional filtering and sorting.

    Request Params:
        page (default: 0)
        size (default: 5)
        filter (optional): Filter tasks by isCompleted or priority.
        value (optional): Value to filter by (e.g., "true" for completed tasks, "HIGH" for priority).
        sort (default: "priority"): Sorting parameter.

Example request:
```
GET /tasks?filter=isCompleted&value=true&sort=priority&page=0&size=5
```

## GET /tasks/{taskId}

Fetch a specific task by its ID.

Example request:
```
GET /tasks/1
```

## POST /tasks

Create a new task. You need to provide a task in the request body (in JSON format).

Example request:
```
POST /tasks
{
  "name": "Fix bugs",
  "description": "Fix the bugs in the project.",
  "priority": "HIGH",
  "isCompleted": false
}
```

## PUT /tasks/{taskId}

Update an existing task by its ID. Provide the updated task data in the request body.

Example request:
```
PUT /tasks/1
{
  "name": "Finish Project - Updated",
  "description": "Updated task description.",
  "priority": "MEDIUM",
  "isCompleted": true
}
```

## DELETE /tasks/{taskId}

Delete a task by its ID.

Example request:
```
DELETE /tasks/1
```

External Libraries and Tools Used

    Spring Boot: Framework for building the RESTful API.
    Spring Data JPA: To interact with the PostgreSQL database using Java Persistence API (JPA).
    Jakarta Validation: Used for input validation (e.g., @Valid, @NotNull).
    Stanford CoreNLP: For sentiment analysis.
    PostgreSQL: The relational database for storing task data.
    H2 Database: Used for testing in-memory database.
    Lombok: Library to reduce boilerplate code (e.g., getters, setters, constructors).

Future Improvement Ideas

    Task Prioritization Algorithm: Implement a smart algorithm to automatically prioritize tasks based on sentiment analysis
    Authentication & Authorization: Add user authentication (JWT or OAuth2) to manage access to task data.
    Advanced Filtering: Allow more advanced filtering options (e.g., filter by task name or description keywords).
    Unit Tests: Improve the test coverage with more comprehensive unit and integration tests.

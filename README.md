# CodeCluster Examination Service

## Overview

The **Examination Service** is a core backend microservice of the
CodeCluster platform. It manages the complete assessment lifecycle,
including assessment creation, question management, mapping questions to
assessments, candidate attempts, and answer submission.

The service exposes REST APIs and uses PostgreSQL as its persistent data
store. It is implemented using Spring Boot, Spring Data JPA, Hibernate,
Bean Validation, and Maven.

Its primary responsibility is to provide the backend examination domain
required by the CodeCluster platform while keeping assessment, question,
attempt, and submission operations organized into separate service
layers.

------------------------------------------------------------------------

## Role in the CodeCluster Project

The Examination Service acts as the **central examination-domain
service** of CodeCluster.

It provides the functionality required to:

-   Create and manage assessments.
-   Maintain a reusable question bank.
-   Support MCQ, coding, and descriptive question types.
-   Associate questions with assessments in a defined order and mark
    allocation.
-   Start and track candidate examination attempts.
-   Submit and finalize attempts.
-   Process assessment answers and maintain submission records.
-   Store examination-related data in PostgreSQL.
-   Expose well-defined REST APIs for consumption by the API
    Gateway/frontend and other platform components.

### Overall Contribution

The service converts the examination requirements of CodeCluster into a
structured backend workflow:

``` text
Assessment Creation
        ↓
Question Bank
        ↓
Questions mapped to Assessment
        ↓
Candidate starts Attempt
        ↓
Candidate answers Questions
        ↓
Assessment Submission
        ↓
Attempt / Submission status and score
```

This makes the Examination Service the main component responsible for
the **assessment and examination lifecycle** within the platform.

------------------------------------------------------------------------

## Key Features

### Assessment Management

-   Create assessments.
-   Maintain assessment duration and total marks.
-   Manage assessment publication status.
-   Associate questions with an assessment.
-   Retrieve questions configured for an assessment.

### Question Bank

-   Create questions.
-   Retrieve questions.
-   Filter questions by difficulty.
-   Filter questions by question type.
-   Retrieve an individual question with its details.
-   Retrieve MCQ options.

### Examination Attempts

-   Start an attempt for a candidate.
-   Prevent duplicate attempts where applicable.
-   Retrieve an existing attempt.
-   Submit an attempt.
-   Maintain attempt status and timestamps.

### Answer Submission

The service supports answer processing for different question types
through the submission model:

-   MCQ answers using selected option IDs.
-   Descriptive answers using text responses.
-   Submission records associated with the assessment and question.

### Validation and Error Handling

The service uses Jakarta Bean Validation for request validation and
contains centralized/global exception-handling support along with
controller-level handling for domain errors.

------------------------------------------------------------------------

# Architecture

The service follows a layered Spring Boot architecture.

``` text
                    ┌──────────────────────────┐
                    │       Client / UI         │
                    │   API Gateway / Consumer  │
                    └────────────┬─────────────┘
                                 │ REST
                                 ▼
                    ┌──────────────────────────┐
                    │       Controllers        │
                    │ Assessment / Question    │
                    │ Attempt / Submission     │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │        Services           │
                    │ Business Logic / Rules    │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │       Repositories        │
                    │      Spring Data JPA      │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │        PostgreSQL         │
                    │      Examination Data     │
                    └──────────────────────────┘
```

## Main Layers

### Controller Layer

Receives HTTP requests, validates input, invokes the appropriate
service, and returns HTTP responses.

Controllers include:

-   `AssessmentController`
-   `QuestionController`
-   `QuestionMCQController`
-   `AttemptController`
-   `SubmissionController`

### Service Layer

Contains the examination business logic.

Major services include:

-   `AssessmentService`
-   `AssessmentQuestionService`
-   `QuestionService`
-   `QuestionMCQService`
-   `AssessmentAttemptService`
-   `SubmissionService`

### Repository Layer

Uses Spring Data JPA repositories to interact with PostgreSQL.

Repositories include:

-   `AssessmentRepository`
-   `AssessmentQuestionRepository`
-   `QuestionRepository`
-   `QuestionMCQRepository`
-   `AssessmentAttemptRepository`
-   `SubmissionRepository`
-   `MCQSubmissionRepository`
-   `DescriptiveSubmissionRepository`

### Entity Layer

The main examination entities are:

-   `Assessment`
-   `Question`
-   `AssessmentQuestion`
-   `AssessmentAttempt`
-   `Submission`
-   `MCQOptions`
-   `MCQSubmissionAnswer`
-   `DescriptiveSubmission`

------------------------------------------------------------------------

# Examination Data Flow

## 1. Create Assessment

A client sends assessment information to the service.

``` text
Client
  ↓
POST /api/v1/assessments
  ↓
AssessmentController
  ↓
AssessmentService
  ↓
AssessmentRepository
  ↓
PostgreSQL
  ↓
AssessmentResponse
```

An assessment contains information such as:

-   Title
-   Duration
-   Total marks
-   Status
-   Start/end timestamps

------------------------------------------------------------------------

## 2. Create Questions

Questions are maintained separately as a reusable question bank.

``` text
Client
  ↓
POST /api/v1/questions
  ↓
QuestionController
  ↓
QuestionService
  ↓
QuestionRepository
  ↓
PostgreSQL
```

Questions support:

``` text
MCQ
CODING
DESCRIPTIVE
```

and difficulty levels:

``` text
EASY
MEDIUM
HARD
```

------------------------------------------------------------------------

## 3. Map Questions to an Assessment

Questions are associated with an assessment through the
`AssessmentQuestion` relationship.

The mapping stores:

-   Assessment ID
-   Question ID
-   Display order
-   Marks

``` text
Assessment
     │
     ├── Question 1
     ├── Question 2
     ├── Question 3
     └── Question N
```

This allows the same question bank to be reused across assessments.

------------------------------------------------------------------------

## 4. Start an Attempt

A candidate starts an assessment using:

``` text
POST /api/v1/assessments/{assessmentId}/attempts
```

The service creates an `AssessmentAttempt` containing:

-   Attempt ID
-   Assessment ID
-   Candidate/User ID
-   Attempt status
-   Start timestamp
-   Submission timestamp
-   Total score

Attempt states are:

``` text
IN_PROGRESS
SUBMITTED
```

------------------------------------------------------------------------

## 5. Submit Assessment

The submission endpoint receives the assessment ID, user ID, and
answers.

``` text
Client
  ↓
POST /submission/submitAssessment
  ↓
SubmissionController
  ↓
SubmissionService
  ↓
Question / Assessment repositories
  ↓
Submission repositories
  ↓
PostgreSQL
```

The answer model supports:

``` text
MCQ
 └── selectedOptionId

DESCRIPTIVE
 └── answer text
```

------------------------------------------------------------------------

# API Documentation

Base URL:

``` text
http://localhost:8080
```

> Authentication/authorization is expected to be handled by the wider
> CodeCluster architecture. Several endpoints currently receive the user
> identity through the `X-User-Id` request header.

------------------------------------------------------------------------

## Assessment APIs

### 1. Create Assessment

``` http
POST /api/v1/assessments
```

Header:

``` text
X-User-Id: <UUID>
Content-Type: application/json
```

Request:

``` json
{
  "title": "Java Backend Assessment",
  "durationMinutes": 60,
  "totalMarks": 100
}
```

Response:

``` json
{
  "assessmentId": 1,
  "title": "Java Backend Assessment",
  "durationMinutes": 60,
  "totalMarks": 100,
  "status": "DRAFT"
}
```

Success:

``` text
201 Created
```

------------------------------------------------------------------------

### 2. Add Questions to Assessment

``` http
POST /api/v1/assessments/{assessmentId}/questions
```

Header:

``` text
X-User-Id: <UUID>
Content-Type: application/json
```

Request:

``` json
[
  {
    "questionId": 101,
    "displayOrder": 1,
    "marks": 5
  },
  {
    "questionId": 102,
    "displayOrder": 2,
    "marks": 5
  }
]
```

Success:

``` text
201 Created
```

------------------------------------------------------------------------

### 3. Get Questions from Assessment

``` http
GET /api/v1/assessments/{assessmentId}/questions
```

Header:

``` text
X-User-Id: <UUID>
```

Returns the questions associated with the assessment, including their
question IDs and question types.

------------------------------------------------------------------------

### 4. Update Assessment Status

``` http
PUT /api/v1/assessments/{assessmentId}/status
```

Header:

``` text
X-User-Id: <UUID>
Content-Type: application/json
```

Request:

``` json
{
  "status": "PUBLISHED"
}
```

Supported statuses:

``` text
DRAFT
PUBLISHED
UNPUBLISHED
```

------------------------------------------------------------------------

### 5. Start Assessment Attempt

``` http
POST /api/v1/assessments/{assessmentId}/attempts
```

Header:

``` text
X-User-Id: <UUID>
```

Creates an examination attempt for the specified user.

Success:

``` text
201 Created
```

Possible conflict:

``` text
409 Conflict
```

when an attempt cannot be created because an applicable attempt already
exists.

------------------------------------------------------------------------

# Question APIs

## 6. Create Question

``` http
POST /api/v1/questions
```

Request:

``` json
{
  "questionId": 101,
  "title": "What is dependency injection?",
  "description": "Explain dependency injection in Spring.",
  "type": "DESCRIPTIVE",
  "difficulty": "MEDIUM",
  "marks": 5,
  "isPublic": true
}
```

Supported question types:

``` text
MCQ
CODING
DESCRIPTIVE
```

Supported difficulties:

``` text
EASY
MEDIUM
HARD
```

Success:

``` text
201 Created
```

------------------------------------------------------------------------

## 7. Get Questions

``` http
GET /api/v1/questions
```

Optional filters:

``` text
?difficulty=EASY
?type=MCQ
```

Example:

``` http
GET /api/v1/questions?difficulty=MEDIUM&type=MCQ
```

Header:

``` text
X-User-Id: <UUID>
```

------------------------------------------------------------------------

## 8. Get Question by ID

``` http
GET /api/v1/questions/{questionId}
```

Header:

``` text
X-User-Id: <UUID>
```

Returns the complete question details.

------------------------------------------------------------------------

# MCQ APIs

## 9. Get MCQ Options

``` http
GET /api/v1/mcq/{questionId}
```

Header:

``` text
X-User-Id: <UUID>
```

Response:

``` json
{
  "questionId": 101,
  "options": [
    "Option A",
    "Option B",
    "Option C",
    "Option D"
  ]
}
```

------------------------------------------------------------------------

# Attempt APIs

## 10. Get Attempt

``` http
GET /api/v1/attempts/{attemptId}
```

Header:

``` text
X-User-Id: <UUID>
```

Returns:

``` json
{
  "attemptId": 10,
  "assessmentId": 1,
  "userId": "user-uuid",
  "status": "IN_PROGRESS",
  "startedAt": "2026-08-11T10:00:00+05:30",
  "submittedAt": null,
  "totalScore": null
}
```

------------------------------------------------------------------------

## 11. Submit Attempt

``` http
POST /api/v1/attempts/{attemptId}/submit
```

Header:

``` text
X-User-Id: <UUID>
```

Finalizes the assessment attempt.

Success:

``` text
200 OK
```

Possible conflict:

``` text
409 Conflict
```

if the attempt has already been submitted.

------------------------------------------------------------------------

# Assessment Submission API

## 12. Submit Assessment Answers

``` http
POST /submission/submitAssessment
```

Request:

``` json
{
  "assessmentId": 1,
  "userId": "00000000-0000-0000-0000-000000000001",
  "answers": [
    {
      "questionId": 101,
      "type": "MCQ",
      "selectedOptionId": 501,
      "answer": null
    },
    {
      "questionId": 102,
      "type": "DESCRIPTIVE",
      "selectedOptionId": null,
      "answer": "Dependency injection is a design pattern..."
    }
  ]
}
```

The service validates the submitted questions and stores the
corresponding submission information.

------------------------------------------------------------------------

# Database Model

The service uses PostgreSQL with Hibernate/JPA.

Core tables/entities include:

``` text
assessments
    │
    ├── assessment_questions
    │          │
    │          └── questions
    │                 │
    │                 └── mcq_options
    │
    └── assessment_attempts

submissions
    ├── mcq_submission_answers
    └── descriptive_submissions
```

### Important Relationships

``` text
Assessment
   │
   ├── 1 : N → AssessmentAttempt
   │
   └── 1 : N → AssessmentQuestion

Question
   │
   ├── 1 : N → MCQOptions
   │
   └── 1 : N → AssessmentQuestion

Assessment + Question
        │
        └── Submission
              ├── MCQSubmissionAnswer
              └── DescriptiveSubmission
```

------------------------------------------------------------------------

# Project Structure

``` text
src/
└── main/
    ├── java/
    │   └── com/exam/examination/
    │       ├── aop/
    │       │   └── GlobalExceptionHandler.java
    │       │
    │       ├── controller/
    │       │   ├── AssessmentController.java
    │       │   ├── AttemptController.java
    │       │   ├── QuestionController.java
    │       │   ├── QuestionMCQController.java
    │       │   └── SubmissionController.java
    │       │
    │       ├── dto/
    │       │   ├── request/
    │       │   └── response/
    │       │
    │       ├── entity/
    │       ├── enums/
    │       ├── exception/
    │       ├── mapper/
    │       ├── repository/
    │       └── service/
    │           └── impl/
    │
    └── resources/
        └── application.yml

src/test/
└── java/
    └── com/exam/examination/
        └── controller/
```

------------------------------------------------------------------------

# Technology Stack

  Technology             Purpose
  ---------------------- ---------------------------------
  Java 21                Application development
  Spring Boot 3.4.3      Backend framework
  Spring Web             REST APIs
  Spring Data JPA        Persistence layer
  Hibernate              ORM
  PostgreSQL             Production database
  H2                     Test database
  Spring Validation      Request validation
  Lombok                 Boilerplate reduction
  Spring Boot Actuator   Application monitoring
  Eureka Client          Service discovery integration
  Maven                  Build and dependency management
  Spring WebSocket       WebSocket capability/dependency

### Service Discovery Note

The project includes the Netflix Eureka Client dependency, making the
service compatible with Eureka-based service discovery. In the current
`application.yml`, Eureka registration/fetching is disabled.

### WebSocket Note

The Spring WebSocket dependency is included in the project. The current
source package inspected in this service does not expose a dedicated
WebSocket controller/handler, so WebSocket functionality should not be
considered an implemented API of this service unless additional
configuration exists outside this repository.

------------------------------------------------------------------------

# Configuration

The application runs on:

``` text
Port: 8080
Service name: examination-service
```

The service uses PostgreSQL and Hibernate with:

``` text
spring.jpa.hibernate.ddl-auto=validate
```

This means Hibernate validates the database schema rather than
automatically creating or modifying the production schema.

## Recommended Configuration

Do **not** commit database credentials to Git.

Use environment variables or an external configuration mechanism.

Example:

``` yaml
server:
  port: 8080

spring:
  application:
    name: examination-service

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
```

------------------------------------------------------------------------

# Running the Service

## Prerequisites

Install:

-   Java 21
-   Maven
-   PostgreSQL

Verify Java:

``` bash
java -version
```

Verify Maven:

``` bash
mvn -version
```

------------------------------------------------------------------------

## Clone the Repository

``` bash
git clone <repository-url>
cd CodeCluster-Examination-Service
```

------------------------------------------------------------------------

## Configure Database

Create/configure the PostgreSQL database and provide the connection
values through environment variables or your local configuration.

Example:

``` text
DB_URL=jdbc:postgresql://localhost:5432/codecluster
DB_USERNAME=postgres
DB_PASSWORD=<your-password>
```

------------------------------------------------------------------------

## Build

Using Maven:

``` bash
mvn clean install
```

Or using the Maven wrapper:

### Windows

``` bash
mvnw.cmd clean install
```

### Linux/macOS

``` bash
./mvnw clean install
```

------------------------------------------------------------------------

## Run

``` bash
mvn spring-boot:run
```

Or:

``` bash
java -jar target/examination-0.0.1-SNAPSHOT.jar
```

The service will be available at:

``` text
http://localhost:8080
```

------------------------------------------------------------------------

# Testing

The repository contains controller tests for:

-   Assessment APIs
-   Attempt APIs
-   Question APIs

Run tests using:

``` bash
mvn test
```

The project also includes H2 as a test dependency so tests can use an
isolated database configuration.

------------------------------------------------------------------------

# Error Handling

The service uses HTTP status codes to communicate API outcomes.

Common responses include:

  --------------------------------------------------------------------------
  Status                              Meaning
  ----------------------------------- --------------------------------------
  `200 OK`                            Request completed successfully

  `201 Created`                       Resource successfully created

  `400 Bad Request`                   Invalid request or business validation
                                      failure

  `404 Not Found`                     Requested
                                      assessment/question/attempt/resource
                                      not found

  `409 Conflict`                      Operation conflicts with the current
                                      resource state

  `500 Internal Server Error`         Unexpected server-side error
  --------------------------------------------------------------------------

------------------------------------------------------------------------

# Design Highlights

## Separation of Responsibilities

The application follows a layered architecture:

``` text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

This keeps HTTP handling, business rules, persistence, and database
access separated.

## Reusable Question Bank

Questions are stored independently from assessments. An assessment
references questions through `AssessmentQuestion`.

This design allows questions to be reused across different assessments
instead of duplicating question data.

## Assessment Attempt Tracking

Each candidate attempt is represented separately from the assessment
itself.

This allows the system to track:

-   Candidate
-   Assessment
-   Start time
-   Submission time
-   Attempt status
-   Total score

## Support for Multiple Question Types

The domain model is designed for:

``` text
MCQ
CODING
DESCRIPTIVE
```

MCQ-specific information is stored separately through MCQ
option/submission entities, while descriptive answers have their own
submission model.

------------------------------------------------------------------------

# Future Enhancements

Potential improvements for the service include:

-   Complete centralized authentication/authorization integration.
-   Role-based access control for faculty, institute administrators, and
    candidates.
-   Full assessment retrieval/update/delete APIs where required.
-   Automated scoring and richer result processing.
-   Dedicated coding-question execution integration.
-   Real-time examination updates through WebSocket handlers.
-   API documentation using OpenAPI/Swagger.
-   Improved centralized exception response formatting.
-   Pagination for large question banks.
-   More comprehensive integration and service-level tests.
-   Integration with the CodeCluster API Gateway and service discovery
    in deployed environments.

------------------------------------------------------------------------

# Summary

The **CodeCluster Examination Service** provides the backend foundation
for managing the examination lifecycle.

Its major responsibilities are:

``` text
Assessment Management
        +
Question Bank Management
        +
Question-to-Assessment Mapping
        +
Candidate Attempt Management
        +
Answer Submission
        +
Examination Data Persistence
```

Through these capabilities, the service enables CodeCluster to move from
assessment creation to candidate attempt and submission in a structured,
maintainable, and database-backed workflow.

------------------------------------------------------------------------

## Contribution Summary

The Examination Service contributes the complete backend domain required
to **create assessments, organize questions, conduct candidate attempts,
and process examination submissions**.

It provides the REST APIs, business-service layer, JPA persistence
model, validation, exception handling, and PostgreSQL integration
required for the examination workflow.

This makes it a foundational service for the execution and management of
assessments within the overall CodeCluster platform.

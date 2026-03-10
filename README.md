# E-Learning Platform (Spring Boot + React)

Backend REST APIs and a separate React SPA for a modular Learning Management System (LMS).

## Highlights
- **LMS core**: Courses, lessons, content materials, and enrollments with progress tracking.
- **Learning materials**: Videos, documents, slides, quizzes, assignments, interactive assets attached to courses.
- **User management**: Administrators, instructors, and learners can register, login, and operate with role-aware permissions.
- **Communication tools**: Topic-based threads plus email/chat-style messages so students and teachers can converse.
- **Assessments**: Quizzes/exams/assignments, submission records, feedback, and grading.
- **Progress reporting**: Lesson completions, time-on-task, quiz scores aggregated per learner.
- **Postgres persistence** with Spring Data/JPA and sample data for courses, materials, assessments, progress, and messages.
- **React frontend** under `frontend/` provides dashboards, course browsing, communications, and enrollment forms.

## Backend runtime

### Prerequisites
- Java 21 SDK
- PostgreSQL 14+ running locally or remotely

### Configuration
Spring Boot relies on `src/main/resources/application.properties`. Override defaults with environment variables:

| Env var | Purpose | Default |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/elearning` |
| `SPRING_DATASOURCE_USERNAME` | DB user | `elearning` |
| `SPRING_DATASOURCE_PASSWORD` | DB password | `elearning` |

Ensure the configured user has privileges to create tables, or pre-create the schema.

### Commands

```
mvn -q clean package
mvn spring-boot:run
```

Endpoints:
- `/api/auth/register` and `/api/auth/login` for user onboarding.
- `/api/courses`, `/api/courses/{id}` plus lesson/material routes.
- `/api/enrollments` to enroll students.
- `/api/progress`, `/api/assessments`, `/api/communications`, and `/api/materials` for the remaining LMS flows.
- SpringDoc UI: `/swagger-ui/index.html`

## React frontend

The UI lives in the `frontend/` directory and consumes the backend APIs.

### Quick start

```
cd frontend
npm install
npm run dev
```

Adjust the API base URL via `frontend/.env` (see template) if the backend runs on a non-default port.

## Testing

```
mvn -q test
```

> *Tests currently fail in this environment because Maven lacks permission to create `C:\Users\CodexSandboxOffline\.m2\repository`. Ensure a writable Maven local repository exists before running.*

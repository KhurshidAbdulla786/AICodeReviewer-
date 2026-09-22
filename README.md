# 🤖 AI Code Reviewer

A production-ready, full-stack AI-powered code review application built with **Java 17**, **Spring Boot 3**, **React + Vite**, and **OpenAI/Gemini** integration.

---

## 📅 Development Phases

| Phase | Milestone | Status |
|-------|-----------|--------|
| Project Setup | Monorepo scaffolding & Docker orchestration | ✅ |
| Backend Core | Spring Boot server, PostgreSQL models (User, Project) | ✅ |
| Authentication | JWT auth, email verification, Google OAuth, protected routes | ⬜ |
| Code Analyzer | Source file upload, AI-powered analysis, scoring | ⬜ |
| AI Integration | OpenAI/Gemini integration via Spring AI | ⬜ |
| Review Matching | Code complexity & bug detection matching | ⬜ |
| Suggestions & Fixes | Code refactoring & personalized improvement roadmaps | ⬜ |
| AI Code Chat | RAG-based AI assistant for code modifications | ⬜ |
| Code Simulator | Review simulator with interactive AI feedback | ⬜ |
| Frontend Polish | Navbar, Sidebar, Dashboard, Landing, Google login | ⬜ |
| Documentation | README, API docs, architecture diagrams | ⬜ |

---
## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (React + Vite)                   │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌───────────────┐  │
│  │ Dashboard│ │ Projects │ │  Editor  │ │   AI Chat     │  │
│  └──────────┘ └──────────┘ └──────────┘ └───────────────┘  │
│                     │ REST API (Axios)                       │
├─────────────────────────────────────────────────────────────┤
│                  Backend (Spring Boot 3)                     │
│  ┌─────────────────────────────────────────────────────┐    │
│  │           API Gateway / Security (JWT)              │    │
│  ├─────────────────────────────────────────────────────┤    │
│  │  Controller Layer → Service Layer → Repository      │    │
│  │           ↓              ↓              ↓            │    │
│  │         DTOs         Entities        Spring AI      │    │
│  └─────────────────────────────────────────────────────┘    │
│                        │                                     │
├─────────────────────────────────────────────────────────────┤
│                   PostgreSQL Database                        │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

### Frontend
- **React 18** with **Vite**
- **Tailwind CSS** for UI
- **Monaco Editor** for code editing
- **React Router v6** for routing
- **Axios** with JWT interceptor and auto-refresh
- **Lucide React** icons & **React Hot Toast**

### Backend
- **Java 17** with **Spring Boot 3**
- **Spring Security** with JWT authentication
- **Spring AI** for OpenAI/Gemini integration
- **Spring Data JPA** with Hibernate
- **PostgreSQL** + **Flyway** migrations
- **Lombok** + **OpenAPI/Swagger** documentation

### DevOps
- **Docker** multi-stage builds
- **Docker Compose** orchestration
- **GitHub Actions** CI/CD pipeline

---

## ⚡ Quick Start

### Prerequisites
- Java 17+
- Node.js 20+
- PostgreSQL 15+
- Docker (optional)

### Start with Docker (Recommended)

```bash
git clone https://github.com/KhurshidAbdulla786/AICodeReviewer-.git
cd AICodeReviewer-
docker compose up -d
```

| Service     | URL                                    |
|-------------|----------------------------------------|
| Frontend    | http://localhost:5173                  |
| Backend API | http://localhost:8080                  |
| Swagger UI  | http://localhost:8080/swagger-ui.html  |
| PostgreSQL  | localhost:5432                         |

### Manual Start

```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend (new terminal)
cd frontend
npm install && npm run dev
```

---

## 📡 API Endpoints

| Method | Endpoint                        | Description              |
|--------|---------------------------------|--------------------------|
| POST   | `/api/v1/auth/register`         | Register new user        |
| POST   | `/api/v1/auth/login`            | Login user               |
| POST   | `/api/v1/auth/refresh`          | Refresh JWT token        |
| GET    | `/api/v1/projects`              | List all projects        |
| POST   | `/api/v1/projects`              | Create project           |
| POST   | `/api/v1/projects/review`       | Submit code for AI review|
| GET    | `/api/v1/reviews`               | List all reviews         |
| GET    | `/api/v1/reviews/{id}`          | Get review details       |
| POST   | `/api/v1/ai/chat`               | Chat with AI             |
| POST   | `/api/v1/ai/explain`            | Explain code             |
| POST   | `/api/v1/ai/quick-review`       | Quick AI review          |

---

## 🗃️ Database Schema

```
users
  └── projects
       └── source_files
            └── reviews
                 ├── review_suggestions
                 └── bug_detections
  ├── ai_conversations
  ├── refresh_tokens
  └── email_verification_tokens
```

---

## 🔐 Security Features

- JWT access + refresh token pattern
- BCrypt password hashing (12 rounds)
- Google OAuth2 login
- Email verification on registration
- CORS configuration
- Input validation on all endpoints
- SQL injection prevention via JPA

---

## 🚀 Features at a Glance

| Feature | Description |
|---------|-------------|
| 🔐 Auth | JWT, Google OAuth, email verification |
| 📊 Dashboard | Review stats, scores, activity feed |
| 📁 Projects | Create & manage code projects |
| ⌨️ Editor | Monaco editor with file upload |
| 🤖 AI Review | Code quality, SOLID, security, performance scoring |
| 🐛 Bug Detection | Null pointer, SQL injection, memory leaks, XSS |
| 📈 Complexity | Cyclomatic complexity, time/space analysis |
| 💬 AI Chat | Natural language code Q&A |
| 📜 History | Persistent review history & comparison |

---

Built with ❤️ using Java 17, Spring Boot 3, Spring AI, and React

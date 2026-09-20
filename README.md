# 🤖 AI Code Reviewer

A production-ready, full-stack AI-powered code review application built with **Java 17**, **Spring Boot 3**, **React + Vite**, and **OpenAI/Gemini** integration.

---

## 📅 Development Push Plan (Day-by-Day)

This project is being built and pushed incrementally — one meaningful part per day — to keep progress clean, reviewable, and well-documented.

---

### ✅ Day 1 — Project Scaffolding & Base Setup
> **Status:** ✅ Done | **Commit:** `chore: initialize project scaffolding with Maven, Vite, and Docker`

**What's included:**
- `backend/pom.xml` — Spring Boot 3 + Spring AI + Spring Security + JPA + Flyway + Lombok
- `backend/Dockerfile` — Multi-stage Docker build for backend
- `frontend/package.json` — React 18 + Vite + Tailwind CSS + Monaco Editor
- `frontend/vite.config.js`, `tailwind.config.js`, `postcss.config.js`
- `frontend/index.html`
- `docker-compose.yml` — Orchestrates backend, frontend, PostgreSQL
- `.gitignore`, `backend/.gitignore`
- `README.md`

---

### 🔜 Day 2 — Database Schema & Flyway Migrations
> **Status:** ⬜ Pending

**What to push:**
- `backend/src/main/resources/db/migration/V1__initial_schema.sql`
  - Tables: `users`, `projects`, `source_files`, `reviews`, `review_suggestions`, `bug_detections`, `ai_conversations`, `refresh_tokens`
- `backend/src/main/resources/db/migration/V2__add_email_verification_and_google_oauth.sql`
  - Tables: `email_verification_tokens`, google oauth fields on `users`
- `backend/src/main/resources/application.yml` — DB config, JWT config, Spring AI config

**Git command:**
```bash
git add backend/src/main/resources/
git commit -m "feat: add database schema migrations and application config"
git push origin main
```

---

### 🔜 Day 3 — Backend: JPA Entities
> **Status:** ⬜ Pending

**What to push:**
- `entity/User.java`
- `entity/Project.java`
- `entity/SourceFile.java`
- `entity/Review.java`
- `entity/ReviewSuggestion.java`
- `entity/BugDetection.java`
- `entity/AIConversation.java`
- `entity/RefreshToken.java`
- `entity/EmailVerificationToken.java`
- `entity/LanguageType.java` (enum)

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/entity/
git commit -m "feat: add JPA entities for all domain models"
git push origin main
```

---

### 🔜 Day 4 — Backend: Repositories & DTOs
> **Status:** ⬜ Pending

**What to push:**
- All `repository/*.java` — Spring Data JPA repositories
- All `dto/request/*.java` — Request DTOs (Login, Register, CreateProject, SubmitCode, Chat, etc.)
- All `dto/response/*.java` — Response DTOs (JwtAuthResponse, ReviewResponse, ProjectResponse, etc.)

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/repository/ \
        backend/src/main/java/com/aicode/reviewer/dto/
git commit -m "feat: add repositories and request/response DTOs"
git push origin main
```

---

### 🔜 Day 5 — Backend: Security (JWT + Spring Security)
> **Status:** ⬜ Pending

**What to push:**
- `security/JwtTokenProvider.java` — JWT generation & validation
- `security/JwtAuthenticationFilter.java` — Request filter
- `security/CustomUserDetails.java` — UserDetails implementation
- `security/CustomUserDetailsService.java` — UserDetailsService
- `security/SecurityConfig.java` — Spring Security configuration (CORS, CSRF, routes)

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/security/
git commit -m "feat: implement JWT authentication and Spring Security config"
git push origin main
```

---

### 🔜 Day 6 — Backend: Exception Handling & Config
> **Status:** ⬜ Pending

**What to push:**
- `exception/BadRequestException.java`
- `exception/ResourceNotFoundException.java`
- `exception/UnauthorizedException.java`
- `exception/GlobalExceptionHandler.java` — `@ControllerAdvice` global handler
- `config/OpenApiConfig.java` — Swagger/OpenAPI configuration

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/exception/ \
        backend/src/main/java/com/aicode/reviewer/config/
git commit -m "feat: add global exception handling and OpenAPI config"
git push origin main
```

---

### 🔜 Day 7 — Backend: Auth Service & Controller
> **Status:** ⬜ Pending

**What to push:**
- `service/AuthService.java` — Auth service interface
- `service/AuthServiceImpl.java` — Register, login, refresh token, logout, email verification
- `service/EmailService.java` — Email verification sending
- `service/GoogleOAuthService.java` — Google OAuth token verification
- `controller/AuthController.java` — `/api/v1/auth/**` endpoints

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/service/AuthService.java \
        backend/src/main/java/com/aicode/reviewer/service/AuthServiceImpl.java \
        backend/src/main/java/com/aicode/reviewer/service/EmailService.java \
        backend/src/main/java/com/aicode/reviewer/service/GoogleOAuthService.java \
        backend/src/main/java/com/aicode/reviewer/controller/AuthController.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/GoogleLoginRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/LoginRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/RegisterRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/RefreshTokenRequest.java
git commit -m "feat: implement authentication service and controller (JWT, Google OAuth, email verification)"
git push origin main
```

---

### 🔜 Day 8 — Backend: Project Service & Controller
> **Status:** ⬜ Pending

**What to push:**
- `service/ProjectService.java` — Create/list/update/delete projects + submit code for review
- `controller/ProjectController.java` — `/api/v1/projects/**` endpoints

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/service/ProjectService.java \
        backend/src/main/java/com/aicode/reviewer/controller/ProjectController.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/CreateProjectRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/SubmitCodeRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/response/ProjectResponse.java
git commit -m "feat: implement project management service and controller"
git push origin main
```

---

### 🔜 Day 9 — Backend: AI Review Service & Controller
> **Status:** ⬜ Pending

**What to push:**
- `service/AiReviewService.java` — AI review service interface
- `service/AiReviewServiceImpl.java` — Spring AI integration (OpenAI/Gemini), code review, bug detection, refactoring, code explanation
- `controller/ReviewController.java` — `/api/v1/reviews/**` endpoints
- `controller/AiChatController.java` — `/api/v1/ai/**` endpoints (chat, explain, quick review)
- `AiCodeReviewerApplication.java` — Main Spring Boot entry point

**Git command:**
```bash
git add backend/src/main/java/com/aicode/reviewer/service/AiReviewService.java \
        backend/src/main/java/com/aicode/reviewer/service/AiReviewServiceImpl.java \
        backend/src/main/java/com/aicode/reviewer/controller/ReviewController.java \
        backend/src/main/java/com/aicode/reviewer/controller/AiChatController.java \
        backend/src/main/java/com/aicode/reviewer/AiCodeReviewerApplication.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/ChatRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/ExplainRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/request/QuickReviewRequest.java \
        backend/src/main/java/com/aicode/reviewer/dto/response/ReviewResponse.java \
        backend/src/main/java/com/aicode/reviewer/dto/response/SuggestionResponse.java \
        backend/src/main/java/com/aicode/reviewer/dto/response/BugDetectionResponse.java
git commit -m "feat: implement AI review service with Spring AI, bug detection, and chat endpoints"
git push origin main
```

---

### 🔜 Day 10 — Frontend: Base Setup & Routing
> **Status:** ⬜ Pending

**What to push:**
- `frontend/src/main.jsx` — React app entry point
- `frontend/src/App.jsx` — Root component with React Router routes
- `frontend/src/index.css` — Global styles with Tailwind
- `frontend/src/api/axios.js` — Axios instance with JWT interceptor + auto-refresh
- `frontend/src/contexts/AuthContext.jsx` — Global auth state (login, logout, user)

**Git command:**
```bash
git add frontend/src/main.jsx \
        frontend/src/App.jsx \
        frontend/src/index.css \
        frontend/src/api/axios.js \
        frontend/src/contexts/AuthContext.jsx
git commit -m "feat: add frontend base setup - routing, axios client, and auth context"
git push origin main
```

---

### 🔜 Day 11 — Frontend: Auth Pages (Login, Register, Verify Email)
> **Status:** ⬜ Pending

**What to push:**
- `frontend/src/pages/Landing.jsx` — Landing/home page
- `frontend/src/pages/Login.jsx` — Login form with JWT auth
- `frontend/src/pages/Register.jsx` — Registration form
- `frontend/src/pages/VerifyEmail.jsx` — Email verification page
- `frontend/src/components/GoogleButton.jsx` — Google OAuth sign-in button

**Git command:**
```bash
git add frontend/src/pages/Landing.jsx \
        frontend/src/pages/Login.jsx \
        frontend/src/pages/Register.jsx \
        frontend/src/pages/VerifyEmail.jsx \
        frontend/src/components/GoogleButton.jsx
git commit -m "feat: add authentication pages (login, register, email verify, landing)"
git push origin main
```

---

### 🔜 Day 12 — Frontend: Layout & Dashboard
> **Status:** ⬜ Pending

**What to push:**
- `frontend/src/components/Layout.jsx` — Sidebar layout with navigation
- `frontend/src/pages/Dashboard.jsx` — Stats overview: total reviews, avg score, recent activity

**Git command:**
```bash
git add frontend/src/components/Layout.jsx \
        frontend/src/pages/Dashboard.jsx
git commit -m "feat: add app layout with sidebar navigation and dashboard page"
git push origin main
```

---

### 🔜 Day 13 — Frontend: Projects & Code Editor
> **Status:** ⬜ Pending

**What to push:**
- `frontend/src/pages/Projects.jsx` — Create/list/delete projects
- `frontend/src/pages/Editor.jsx` — Monaco code editor with file upload and AI review trigger

**Git command:**
```bash
git add frontend/src/pages/Projects.jsx \
        frontend/src/pages/Editor.jsx
git commit -m "feat: add project management page and Monaco code editor with AI review"
git push origin main
```

---

### 🔜 Day 14 — Frontend: Review History & Profile
> **Status:** ⬜ Pending

**What to push:**
- `frontend/src/pages/ReviewHistory.jsx` — Past reviews list with scores and details
- `frontend/src/pages/Profile.jsx` — User profile and settings

**Git command:**
```bash
git add frontend/src/pages/ReviewHistory.jsx \
        frontend/src/pages/Profile.jsx
git commit -m "feat: add review history page and user profile page"
git push origin main
```

---

### 🔜 Day 15 — Frontend: AI Chat & Markdown Renderer
> **Status:** ⬜ Pending

**What to push:**
- `frontend/src/pages/AiChat.jsx` — Real-time AI chat for code Q&A
- `frontend/src/components/MarkdownMessage.jsx` — Markdown renderer for AI responses

**Git command:**
```bash
git add frontend/src/pages/AiChat.jsx \
        frontend/src/components/MarkdownMessage.jsx
git commit -m "feat: add AI chat page with markdown rendering for AI responses"
git push origin main
```

---

### 🔜 Day 16 — CI/CD Pipeline & Final Polish
> **Status:** ⬜ Pending

**What to push:**
- `.github/workflows/ci-cd.yml` — GitHub Actions pipeline (build, test, Docker push)
- `frontend/package-lock.json`
- `db_tables.txt`, `flyway_history.txt` — Documentation files
- Final `README.md` updates

**Git command:**
```bash
git add .github/ \
        frontend/package-lock.json \
        db_tables.txt \
        flyway_history.txt \
        README.md
git commit -m "feat: add GitHub Actions CI/CD pipeline and finalize project"
git push origin main
```

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

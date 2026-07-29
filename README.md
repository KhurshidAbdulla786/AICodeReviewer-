# AI Code Reviewer

A production-ready, full-stack AI-powered code review application built with Java 21, Spring Boot 3, React, and OpenAI/Gemini integration.

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Frontend (React + Vite)                │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌───────────────┐  │
│  │ Dashboard│ │ Projects│ │  Editor  │ │   AI Chat     │  │
│  └──────────┘ └──────────┘ └──────────┘ └───────────────┘  │
│                        │ REST API (Axios)                   │
├─────────────────────────────────────────────────────────────┤
│                   Backend (Spring Boot 3)                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │           API Gateway / Security (JWT)               │    │
│  ├─────────────────────────────────────────────────────┤    │
│  │  Controller Layer → Service Layer → Repository      │    │
│  │           ↓              ↓              ↓            │    │
│  │         DTOs         Mappers        Entities         │    │
│  ├─────────────────────────────────────────────────────┤    │
│  │         Spring AI (OpenAI/Gemini Integration)        │    │
│  └─────────────────────────────────────────────────────┘    │
│                        │                                     │
├─────────────────────────────────────────────────────────────┤
│                    PostgreSQL Database                       │
└─────────────────────────────────────────────────────────────┘
```

## Tech Stack

### Frontend
- **React 18** with **Vite** for blazing-fast development
- **Tailwind CSS** for modern, responsive UI
- **Monaco Editor** for code editing with syntax highlighting
- **React Router v6** for client-side routing
- **Axios** with JWT interceptor and auto-refresh
- **Lucide React** icons
- **React Hot Toast** notifications

### Backend
- **Java 21** with Spring Boot 3.2
- **Spring Security** with JWT authentication
- **Spring AI** for OpenAI/Gemini integration
- **Spring Data JPA** with Hibernate
- **PostgreSQL** database
- **Flyway** for database migrations
- **Lombok** for boilerplate reduction
- **OpenAPI/Swagger** documentation
- **JUnit 5 + Mockito** testing

### DevOps
- **Docker** multi-stage builds
- **Docker Compose** orchestration
- **GitHub Actions** CI/CD pipeline
- **JaCoCo** code coverage

## Features

### 🔐 Authentication & Authorization
- User registration & login
- JWT access & refresh tokens
- Role-based access (USER, ADMIN)
- Secure password hashing with BCrypt

### 📊 Dashboard
- Total reviews counter
- Average code quality score
- Most reviewed language
- Recent reviews timeline

### 📁 Project Management
- Create, rename, delete projects
- Organize code files per project
- Multi-language support

### ⌨️ Code Editor
- Monaco Editor integration
- Multiple language support (Java, Python, JS, TS, C++, C#, Go)
- Dark theme with syntax highlighting
- File upload support
- Auto-save functionality

### 🤖 AI Code Review
- Sends code to OpenAI/Gemini API
- Analyzes: readability, naming conventions, code style
- SOLID principles & OOP design evaluation
- Detects: dead code, duplicate code, exception handling
- Security & performance analysis
- Overall quality score (0-100)
- Actionable improvement suggestions

### 🐛 Bug Detection
- Null pointer risks
- Infinite loops
- SQL injection & XSS vulnerabilities
- Memory & resource leaks
- Division by zero detection
- Index out of bounds checks

### 📈 Complexity Analysis
- Cyclomatic complexity
- Lines of code stats
- Class & method counts
- Time & space complexity estimates
- Maintainability score

### 🔄 Code Refactoring
- AI-generated improved code
- One-click code replacement
- Detailed explanation of improvements

### 💬 AI Chat
- Natural language code queries
- Code explanation & optimization
- Bug finding & Stream API conversion
- OOP violation detection

### 📜 Review History
- Persistent storage of all reviews
- Search & filter past reviews
- Detailed review comparison

### 👑 Admin Dashboard
- User management
- System statistics
- API usage monitoring
- Review analytics

## Quick Start

### Prerequisites
- Java 21+
- Node.js 20+
- PostgreSQL 16+
- Docker (optional)

### 1. Environment Setup

```bash
# Clone repository
git clone <repo-url>
cd ai-code-reviewer

# Backend environment variables
cp backend/.env.example backend/.env
# Edit backend/.env with your values:
# - OPENAI_API_KEY or GEMINI_API_KEY
# - JWT_SECRET
# - Database credentials

# Frontend environment variables
cp frontend/.env.example frontend/.env
```

### 2. Start with Docker (Recommended)

```bash
docker compose up -d
```

This starts:
- Backend API at `http://localhost:8080`
- Frontend app at `http://localhost:5173`
- PostgreSQL at `localhost:5432`
- Swagger UI at `http://localhost:8080/swagger-ui.html`

### 3. Manual Setup

#### Backend

```bash
cd backend

# Build the project
./mvnw clean package -DskipTests

# Run tests
./mvnw test

# Start application
./mvnw spring-boot:run
```

#### Frontend

```bash
cd frontend

# Install dependencies
npm install

# Development server
npm run dev

# Production build
npm run build
```

## API Documentation

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login user |
| POST | `/api/v1/auth/refresh` | Refresh JWT token |
| POST | `/api/v1/auth/logout` | Logout user |
| PUT | `/api/v1/auth/profile` | Update profile |
| PUT | `/api/v1/auth/change-password` | Change password |

### Project Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/projects` | List all projects |
| POST | `/api/v1/projects` | Create project |
| GET | `/api/v1/projects/{id}` | Get project details |
| PUT | `/api/v1/projects/{id}` | Update project |
| DELETE | `/api/v1/projects/{id}` | Delete project |
| POST | `/api/v1/projects/review` | Submit code for AI review |

### Review Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/reviews` | List all reviews |
| GET | `/api/v1/reviews/{id}` | Get review details |
| DELETE | `/api/v1/reviews/{id}` | Delete review |

### AI Chat Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/ai/chat` | Send message to AI |
| POST | `/api/v1/ai/explain` | Explain code |
| POST | `/api/v1/ai/optimize` | Optimize code |
| POST | `/api/v1/ai/find-bugs` | Find bugs in code |

### API Response Format

```json
{
  "success": true,
  "message": "Operation completed",
  "data": {
    // Response payload
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### Authentication Example

```bash
# Register
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'

# Use JWT
curl http://localhost:8080/api/v1/projects \
  -H "Authorization: Bearer <jwt_token>"
```

## Project Structure

```
├── backend/
│   ├── src/main/java/com/aicode/reviewer/
│   │   ├── config/          # App configuration
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Request/Response DTOs
│   │   ├── entity/          # JPA entities
│   │   ├── exception/       # Global exception handling
│   │   ├── mapper/          # Entity-DTO mappers
│   │   ├── repository/      # Data repositories
│   │   ├── security/        # JWT + Spring Security
│   │   └── service/         # Business logic
│   ├── src/main/resources/
│   │   └── db/migration/    # Flyway migrations
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── api/             # Axios client setup
│   │   ├── components/      # Reusable components
│   │   ├── contexts/        # React contexts
│   │   └── pages/           # Page components
│   ├── index.html
│   ├── vite.config.js
│   ├── tailwind.config.js
│   └── package.json
├── docker-compose.yml
├── .github/workflows/       # CI/CD pipeline
└── README.md
```

## Database Schema

```
User (id, name, email, password, role, created_at)
  └── Project (id, name, description, language, user_id, created_at)
       └── SourceFile (id, name, code, language, project_id, created_at)
            └── Review (id, source_file_id, overall_score, status, created_at)
                 └── ReviewSuggestion (id, review_id, category, severity, suggestion)
                 └── BugDetection (id, review_id, bug_type, line_number, description)
  └── AIConversation (id, user_id, message, response, created_at)
  └── RefreshToken (id, user_id, token, expires_at)
```

## Security

- **JWT-based authentication** with access/refresh token pattern
- **BCrypt** password encryption (12 rounds)
- **Rate limiting** on API endpoints
- **CORS** configuration for frontend origin
- **Input validation** on all endpoints
- **SQL injection** prevention via JPA
- **XSS** protection
- **CSRF** protection for state-changing operations

## Testing

```bash
# Run all tests
cd backend && ./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTest

# Coverage report
./mvnw verify

# View coverage
open target/site/jacoco/index.html
```

## Deployment

### Docker Deployment

```bash
# Build and start all services
docker compose up -d --build

# Scale services
docker compose up -d --scale backend=2

# View logs
docker compose logs -f

# Stop services
docker compose down
```

### Production Considerations

1. Set strong `JWT_SECRET` (min 256-bit)
2. Configure proper `CORS` origins
3. Enable HTTPS with SSL certificate
4. Set up database connection pooling
5. Configure proper logging levels
6. Set up monitoring (Prometheus/Grafana)
7. Regular database backups
8. Rate limiting configuration

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

MIT License - see [LICENSE](LICENSE) file.

---

Built with ❤️ using Java 21, Spring Boot 3, and React

# 📦 GitHub User Service

A Spring Boot REST API service and React Front App that fetches public GitHub users, maps them into local data models, and stores them in a SQL database. It supports scheduled updates

---

## 🚀 Getting Started

### Prerequisites

1. **Java 17+**
2. **Maven 3.8+**
3. **PostgreSQL** (or any other SQL DB)
4. **Git**


---

### 🏗️ Installation & Setup

1. **Clone the repository:**
   ```shell
   git git@github.com:hinciu/git-hub-user-service.git
   cd git-hub-user-service
   ```
   Make sure to set GITHUB_API_TOKEN env variable with your GitHub token.

2. **Configure the database:**
    - Open `application.properties` and update the database credentials:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/github_users
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

3. **Run the service:**
   ```shell
   mvn spring-boot:run
   ```

4. **Access the API:**
    - API Base URL: `http://localhost:8080/api/v1/users`

---

### 🛠️ Features

1. **Fetch GitHub Users Automatically**
    - Scheduled every X minutes to update. Configurable value
    - Displays paginated table of users
    - Requires to login in order display users


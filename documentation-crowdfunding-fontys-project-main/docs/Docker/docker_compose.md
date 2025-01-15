---
sidebar_position: 7
sidebar_label: "Compose setup"
---

# RaiseHub Application Deployment

This guide provides step-by-step instructions to deploy the **RaiseHub** application using Docker Compose. The setup includes three services: a MySQL database, a backend service, and a frontend service.

---

## Docker Compose Configuration

Below is the Docker Compose configuration for the `RaiseHub` application:

```yaml
version: '3.8'

services:
  raisehub-database:
    image: mateogonzalezz/raisehub_database
    volumes:
      - mysql-data:/var/lib/mysql
    container_name: raisehub_database
    ports:
      - 3307:3306
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping", "-h", "localhost" ]
      interval: 10s
      timeout: 5s
      retries: 5

  raisehub-backend:
    image: mateogonzalezz/raisehub_backend
    container_name: raisehub_backend
    environment:
      - JWT_SECRET=<value>
      - MYSQL_USERNAME=<value>
      - MYSQL_PASSWORD=<value>
    ports:
      - 8080:8080
    depends_on:
      raisehub-database:
        condition: service_healthy

  nameless-frontend:
    image: mateogonzalezz/raisehub_frontend
    container_name: raisehub_frontend
    environment:
      - AWS_BUCKET_NAME=<value>
      - AWS_BUCKET_REGION=<value>
      - AWS_ACCESS_KEY=<value>
      - AWS_SECRET_ACCESS_KEY=<value>
    ports:
      - 3000:3000
    depends_on:
      - raisehub-backend

volumes:
  mysql-data:

name: raisehub-stack
```

---

## Services Overview

### 1. **MySQL Database Service** (`raisehub-database`)

- **Image**: `mateogonzalezz/raisehub_database`
- **Volumes**: Stores MySQL data in a persistent volume `mysql-data`.
- **Ports**: Exposes the database on port `3307`.
- **Health Check**: Ensures the database is ready to accept connections before dependent services start.

### 2. **Backend Service** (`raisehub-backend`)

- **Image**: `mateogonzalezz/raisehub_backend`
- **Container Name**: `raisehub_backend`
- **Ports**: Exposes the backend on port `8080`.
- **Environment Variables**:
  - `JWT_SECRET`: Secret key for JWT authentication.
  - `MYSQL_USERNAME` and `MYSQL_PASSWORD`: Credentials for the MySQL database.
- **Dependencies**: Depends on the database service (`raisehub-database`) and starts only when the database is healthy.

### 3. **Frontend Service** (`nameless-frontend`)

- **Image**: `mateogonzalezz/raisehub_frontend`
- **Container Name**: `raisehub_frontend`
- **Environment Variables**:
  - `AWS_BUCKET_NAME`, `AWS_BUCKET_REGION`, `AWS_ACCESS_KEY`, `AWS_SECRET_ACCESS_KEY`: Configuration for AWS S3 bucket.
- **Ports**: Exposes the frontend on port `3000`.
- **Dependencies**: Depends on the backend service (`raisehub-backend`).

---

## Deployment Instructions

1. **Clone the Repository**  
   Ensure the `docker-compose.yml` file is present in your working directory.

2. **Start the Services**  
   Run the following command to start all services:

   ```bash
   docker-compose up -d
   ```

3. **Verify the Services**  
   - Database: Accessible on `localhost:3307`.
   - Backend: Accessible on `localhost:8080`.
   - Frontend: Accessible on `localhost:3000`.

4. **Stop the Services**  
   To stop and remove the services, run:

   ```bash
   docker-compose down
   ```

---

## Persistent Data Storage

The database service uses a Docker volume named `mysql-data` to store data persistently. This ensures that data is retained even if the container is removed.

---

## Troubleshooting

- **Service Not Starting**: Ensure Docker and Docker Compose are installed and running on your system.
- **Port Conflicts**: If the default ports (`3307`, `8080`, or `3000`) are in use, modify them in the `docker-compose.yml` file.
- **Connection Issues**: Verify that the services are running using the `docker ps` command.

---
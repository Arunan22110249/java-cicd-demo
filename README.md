# Java CI/CD Demo — Campus Food Demand Predictor

This is a minimal, runnable Java Spring Boot project demonstrating an end-to-end CI/CD-ready repository:
- Maven build
- Unit test
- Dockerfile
- Jenkinsfile (pipeline)
- README + .gitignore

**Real-world problem solved:** *Campus Food Demand Predictor for a small food stall.*  
The app allows staff to record daily sales for menu items and provides a simple demand prediction endpoint (moving average) to help plan ingredient prep and reduce waste.

## What is included
- `src/` — Spring Boot application with REST endpoints
- `pom.xml` — Maven config
- `Dockerfile` — Multi-stage build
- `Jenkinsfile` — Declarative pipeline for build, test, docker image build & push (example)
- Unit tests under `src/test/`

## Build & Run locally
Requirements: Java 17, Maven 3.8+

```bash
mvn clean package
java -jar target/java-cicd-demo-0.0.1-SNAPSHOT.jar
# API: GET http://localhost:8080/actuator/health
# API: GET http://localhost:8080/api/items
```

## Docker
```bash
mvn -DskipTests clean package
docker build -t yourdockeruser/java-cicd-demo:0.0.1 .
docker run -p 8080:8080 yourdockeruser/java-cicd-demo:0.0.1
```

## Jenkins notes
The provided `Jenkinsfile` is a practical starting pipeline. You'll need to add credentials in Jenkins:
- `dockerhub-credentials-id` for Docker Hub (username/password)
- `ssh-deploy-cred` if you want SSH-based deploy

## Endpoints (examples)
- `GET /api/items` — list tracked menu items and historical sales
- `POST /api/items/{itemId}/sales` — record sales for a date
- `GET /api/items/{itemId}/predict?days=3` — predict next-day demand using moving average of last `days` entries

## License
MIT

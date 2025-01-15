---
sidebar_position: 3
sidebar_label: Pipeline diagram
---

# Back-end pipeline diagram

![Pipeline diagram](img/BE_pipelineDiagram.png)

The diagram illustrates a CI/CD pipeline process involving multiple stages:

1. **Code Submission**:
   - Developers write code on their local PC and push it to a GitHub server.

2. **Java CI with Gradle**:
   - **Build**: The Java project is built using Gradle.
   - **Test**: The application is tested to ensure code quality and functionality.

3. **SonarQube Analyze**:
   - **Build**: The code undergoes a build process for analysis.
   - **Analysis**: SonarQube analyzes the code for bugs, vulnerabilities, and code smells.
   - **Quality

4. **Docker Image CI**:
   - **Build**: The pipeline builds the Docker image.
   - **Build Docker Image**: The Docker image is built.
   - **Push Image to Docker Hub**: The built Docker image is pushed to Docker Hub for storage.

5. **Final Outcome**:
   - If all steps succeed (Docker, Gradle, and SonarQube checks), the pipeline passes ✅.      
   - If any step fails, the pipeline fails ❌.
      - In case any of the steps fails, all the next jobs are skipped and the pipeline fails.
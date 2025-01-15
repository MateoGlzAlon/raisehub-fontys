---
  sidebar_position: 1
  sidebar_label: End-to-End Testing
---

# End-to-End Testing

## Overview

End-to-End (E2E) testing ensures that the entire application, including both the frontend and backend, works as expected in a real-world environment. This guide demonstrates how the **Cypress** testing framework is used to perform E2E testing on a full-stack application. It covers the testing of the **frontend** user interface, **backend** APIs, and the integration between the **database**, **backend**, and **frontend**.

## Pipeline Configuration

The following GitHub Actions pipeline configuration automates the process of running E2E tests. The pipeline sets up the necessary services and performs testing whenever changes are pushed to the `main` branch or a pull request is created.

```yaml
name: End-to-End Testing

on:
  push:
    branches:
      - main
  pull_request:

jobs:
  cypress-tests:
    runs-on: ubuntu-latest    

    steps:
      # Step 1: Checkout the code
      - name: Checkout code
        uses: actions/checkout@v3

      # Step 2: Set up Node.js
      - name: Set up Node.js
        uses: actions/setup-node@v3
        with:
          node-version: 18

      # Step 1: Log in to Docker Hub
      - name: Log in to Docker Hub
        run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin

      # Step : Connect containers
      - name: Create container network
        run: |
          docker network create docker_network_crowdfund

      # Step 3: Pull and Run Database Docker Image
      - name: Run MySQL database service
        run: |
          docker pull ${{ secrets.DOCKER_USERNAME }}/raisehub_database:latest
          docker run -d --name raisehub_database \
            -p 3306:3306 \
            --env MYSQL_ROOT_PASSWORD=${{ secrets.MYSQL_ROOT_PASSWORD }} \
            --env MYSQL_DATABASE=crowdfund_db \
            ${{ secrets.DOCKER_USERNAME }}/raisehub_database:latest
          docker network connect docker_network_crowdfund raisehub_database

      # Step 3.1: Wait for MySQL to be ready using dockerize
      - name: Wait for MySQL to be ready
        run: |
          sleep 30
          echo "MySQL is ready"

      # Step 4: Pull and Run Backend Docker Image
      - name: Run backend service
        run: |
          docker pull ${{ secrets.DOCKER_USERNAME }}/raisehub_backend:latest
          docker run -d --name backend \
            -p 8080:8080 \
            --env MYSQL_USERNAME=${{ secrets.MYSQL_USERNAME }} \
            --env MYSQL_PASSWORD=${{ secrets.MYSQL_PASSWORD }} \
            --env JWT_SECRET=${{ secrets.JWT_SECRET }} \
            ${{ secrets.DOCKER_USERNAME }}/raisehub_backend:latest            
          docker network connect docker_network_crowdfund backend

      # Step 4.1: Wait for Backend to be ready using dockerize
      - name: Wait for Backend Service to be ready
        run: |
          sleep 1

      # Step 5: Build and Serve the Frontend
      - name: Build and Serve Frontend
        run: |
          npm install
          npm run build
          npm run start & # Start frontend in background        

      # Step 6: Wait for Frontend Service to Start using dockerize
      - name: Wait for Frontend Service to Start
        run: |
          sleep 15
          echo "Frontend is ready"

      # Step 7: Run Cypress Tests
      - name: Run Cypress tests
        uses: cypress-io/github-action@v5
        with:
          browser: chrome
          config-file: cypress.config.js
        env:
          CYPRESS_baseUrl: http://localhost:3000
```

### Key Features:
- **Dockerized Services**: The database and backend services are dockerized to isolate them from the local environment, ensuring that tests are consistent across different environments.
- **Parallel Execution**: Cypress tests run in a browser environment, ensuring the user interface works as expected.
- **Wait for Services**: The pipeline includes waiting steps to ensure the services (frontend, backend, and database) are fully up and ready before tests are executed.

## Example E2E Test

### Test: `Make a Payment`

This test simulates a user visiting the site, entering an amount, and confirming the payment.

```javascript
it('Make a payment', () => {
    // Visit the project page
    cy.get('#project-card').click()

    // Type an amount into the input field
    cy.get('#inputMoneyAmount')
        .type('125')
        .should('have.value', '125') // Ensure the input contains the value '125'

    // Click the "Pay" button
    cy.contains('Pay').click()

    // Click the "Confirm" button
    cy.contains('Confirm').click()
});
```

#### Key Details:
- **Test Flow**:
  1. Visit a project page by clicking the project card.
  2. Enter a payment amount in the input field.
  3. Confirm the payment by clicking the "Pay" and "Confirm" buttons.
- **Assertions**:
  - Ensure the money input contains the correct amount (`125`).
  - Ensure the "Pay" and "Confirm" buttons are clicked successfully.


End-to-End tests ensure that your application’s frontend, backend, and database are functioning as expected in a fully integrated environment. By using **Cypress** in a **GitHub Actions** pipeline, we ensure that every push to the `main` branch is tested automatically. This prevents issues from reaching production and ensures a seamless user experience across the entire stack.
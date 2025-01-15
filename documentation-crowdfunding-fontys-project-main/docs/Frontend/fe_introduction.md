---
sidebar_position: 1
sidebar_label: Introduction
---

# Front-end Introduction

This section provides an overview of the front-end architecture and tools used for the "RaiseHub" project. It covers the technologies, development practices, and structure of the front-end codebase, along with instructions for setting up the development environment.

## Overview

The front-end of the "RaiseHub" application is a Single-Page Application (SPA) built using modern web development frameworks and libraries. It provides an intuitive interface for users to create, fund, and manage projects. The goal is to deliver a seamless user experience.

## Technologies Used

The front-end is built using the following technologies:
- **React**: A JavaScript library for building user interfaces. React's component-based architecture makes it easier to manage complex UIs.
- **React Router**: Enables client-side routing to create a multi-page feel within a single-page application.
- **TailwindCSS** (choose the one you use): For styling the user interface. It provides pre-built components and styles that speed up development.
- **Axios**: For making HTTP requests to the back-end API.

## Project Structure

The codebase follows a modular structure, organized into different folders:
- **`components/`**: Contains reusable UI components such as buttons, forms, and cards.
- **`app/`**: Includes the main views or screens, such as Home, Project Details, and User Dashboard.
- **`public/`**: Stores most of the images of the page(not individual project images)

## Setting Up the Front-end Development Environment

Follow these steps to set up the front end for development:

1. **Prerequisites**
   - Node.js (v18 or higher)    
   - npm
   

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/MateoGlzAlon/ui-crowfunding-fontys-project.git
   ```

3. **Install Dependencies**:
   Run the following command to install the required dependencies:
   ```bash
   npm install
   ```

   
4. **Create an .env file**:
   Create an .env file in the root directory for the environment variables
   ```bash, title=".env"
    AWS_BUCKET_NAME=<value>
    AWS_BUCKET_REGION=<value
    AWS_ACCESS_KEY=<value>
    AWS_SECRET_ACCESS_KEY=<value>
   ```

    **[You can review the environment variables here](environment_variables.md)**

5. **Run the Development Server**:
   Start the development server using:
   ```bash
   npm run dev
   ```

   By default, the application will be available at `http://localhost:3000`.

## Run with Docker (optional):

### 1. **Download the image from the DockerHub Repository**:
    - Download the image from the DockerHub repository:
        [DockerHub repository](https://hub.docker.com/r/mateogonzalezz/raisehub_frontend/tags)

### 2. **Run the Docker Container**:
   - Run the container with the environment variables:
   ```bash
   docker run -d --name raisehub_frontend_container -p 3000:3000 -e AWS_BUCKET_NAME=<value> -e AWS_BUCKET_REGION=<value> -e AWS_ACCESS_KEY=<value> -e AWS_SECRET_ACCESS_KEY=<value> raisehub-frontend
   ```

**[You can review the environment variables here](environment_variables.md)**

The application will be accessible at `http://localhost:3000` by default.
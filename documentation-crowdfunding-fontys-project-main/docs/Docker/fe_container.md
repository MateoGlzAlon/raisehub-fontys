---
sidebar_position: 5
sidebar_label: Create Front-end Image and Container
---

# Creating Docker Image of the Front-end

This document provides instructions on setting up a Docker container for the Crowdfunding project's front-end. The Dockerfile is based on the `node:18-alpine` image and uses a multi-stage build process to optimize the container size.

## Dockerfile Configuration

Below is the configuration for the Dockerfile, including comments explaining each step:

```dockerfile
# Use the official Node.js image as the base for all stages
FROM node:18-alpine AS base

# Set up the dependencies installation stage
FROM base AS deps

# Install compatibility libraries
RUN apk add --no-cache libc6-compat

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json file to the container
COPY package.json ./

# Update npm and install project dependencies
RUN npm update && npm install

# Build the front-end application
FROM base AS builder
WORKDIR /app

# Copy installed node_modules and application files
COPY --from=deps /app/node_modules ./node_modules
COPY . .

# Compile the application
RUN npm run build

# Prepare the production-ready image
FROM base AS runner
WORKDIR /app

# Set environment variables for production
ENV NODE_ENV=production

# Create a system group and user for running the application
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

# Copy compiled application files
COPY --from=builder /app/public ./public
RUN mkdir .next
RUN chown nextjs:nodejs .next
COPY --from=builder --chown=nextjs:nodejs /app/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static

# Set user permissions for security
USER nextjs

# Expose the application port
EXPOSE 3000

# Set the default port for the application
ENV PORT=3000

# Define the command to run the application
CMD ["node", "server.js"]
```

## How to Create the Image

Once the Dockerfile is configured, build the Docker image for the application by running the following command in the root of the project:

```bash
docker build -t <image_name> .
```

### Explanation
- **docker build**: Creates a Docker image from the Dockerfile.
- **-t image_name*: Tags the image with a custom name.
- **.**: Indicates the current directory as the build context.

## Verify the Image

After building the image, verify its existence by listing Docker images or checking Docker Desktop:

```bash
docker images
```

You should see `<image_name>` listed in the output, confirming a successful build.

## Creating and Running the Docker Container

Run the container with the appropriate environment variables:

- Run the container with the environment variables:
   ```bash
   docker run -d --name raisehub_frontend_container -p 3000:3000 -e AWS_BUCKET_NAME=<value> -e AWS_BUCKET_REGION=<value> -e AWS_ACCESS_KEY=<value> -e AWS_SECRET_ACCESS_KEY=<value> <image_name>
   ```

**[You can review the environment variables here](../Frontend/environment_variables.md)**

The application will be accessible at `http://localhost:3000` by default.
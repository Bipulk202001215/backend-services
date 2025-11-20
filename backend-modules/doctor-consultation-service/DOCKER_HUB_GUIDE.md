# Docker Hub Push Guide

This guide explains how to build and push the Doctor Consultation Service Docker image to Docker Hub.

## Prerequisites

1. Docker installed and running
2. Docker Hub account (create one at https://hub.docker.com/)
3. Your Docker Hub username

## Step-by-Step Instructions

### 1. Login to Docker Hub

```bash
docker login
```

Enter your Docker Hub username and password when prompted.

### 2. Build the Docker Image

From the project root directory (`/Users/bipulkumar/projects/doctor-clinic/backendservice`):

```bash
docker build -f backend-modules/doctor-consultation-service/Dockerfile \
  -t your-dockerhub-username/doctor-consultation-service:latest .
```

Replace `your-dockerhub-username` with your actual Docker Hub username.

### 3. Tag the Image (Optional - for versioning)

```bash
# Tag as latest
docker tag your-dockerhub-username/doctor-consultation-service:latest \
  your-dockerhub-username/doctor-consultation-service:latest

# Tag with version number
docker tag your-dockerhub-username/doctor-consultation-service:latest \
  your-dockerhub-username/doctor-consultation-service:1.0.0
```

### 4. Push to Docker Hub

```bash
# Push latest version
docker push your-dockerhub-username/doctor-consultation-service:latest

# Push specific version
docker push your-dockerhub-username/doctor-consultation-service:1.0.0
```

## Using the Script

Alternatively, you can use the provided script:

```bash
# Make script executable
chmod +x backend-modules/doctor-consultation-service/docker-push.sh

# Build only
./backend-modules/doctor-consultation-service/docker-push.sh your-dockerhub-username

# Build and push
./backend-modules/doctor-consultation-service/docker-push.sh your-dockerhub-username latest --push
```

## Pull and Run from Docker Hub

Once pushed, others can pull and run your image:

```bash
# Pull the image
docker pull your-dockerhub-username/doctor-consultation-service:latest

# Run the container
docker run -p 8080:8080 your-dockerhub-username/doctor-consultation-service:latest
```

## Environment Variables

You can override environment variables when running:

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e JAVA_OPTS="-Xms1g -Xmx2g" \
  -e SPRING_DATA_MONGODB_URI="mongodb://your-mongo-host:27017/your-db" \
  your-dockerhub-username/doctor-consultation-service:latest
```

## Troubleshooting

### Authentication Issues
- Make sure you're logged in: `docker login`
- Check your username is correct in the image tag

### Build Failures
- Ensure you're running the build command from the project root
- Check that all dependencies are available

### Push Failures
- Verify you have push permissions to the repository
- Check your internet connection
- Ensure the repository name matches your Docker Hub username


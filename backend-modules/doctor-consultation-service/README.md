# Doctor Consultation Service - Docker Guide

This guide provides useful Docker commands for building, running, and managing the doctor-consultation-service container.

## Prerequisites

- Docker installed and running (Docker Desktop or Colima)
- Maven (for building, though Docker handles this)
- Access to MongoDB (configured in `application.properties`)

## Quick Start

### Build the Docker Image

From the project root directory (`/backendservice`):

```bash
docker build -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:latest .
```

### Run the Container

```bash
docker run -d -p 8080:8080 --name doctor-consultation-service doctor-consultation-service:latest
```

## Docker Commands

### Building

#### Build the image
```bash
docker build -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:latest .
```

#### Build with no cache (force rebuild)
```bash
docker build --no-cache -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:latest .
```

#### Build with a specific tag
```bash
docker build -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:v1.0.0 .
```

### Running

#### Run in detached mode (background)
```bash
docker run -d -p 8080:8080 --name doctor-consultation-service doctor-consultation-service:latest
```

#### Run in foreground (see logs)
```bash
docker run -p 8080:8080 --name doctor-consultation-service doctor-consultation-service:latest
```

#### Run with custom port mapping
```bash
docker run -d -p 9090:8080 --name doctor-consultation-service doctor-consultation-service:latest
```

#### Run with environment variables
```bash
docker run -d -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e JAVA_OPTS="-Xms1g -Xmx2g" \
  -e SPRING_DATA_MONGODB_URI="your-mongodb-uri" \
  --name doctor-consultation-service \
  doctor-consultation-service:latest
```

#### Run with environment file
```bash
docker run -d -p 8080:8080 --env-file .env --name doctor-consultation-service doctor-consultation-service:latest
```

### Container Management

#### List running containers
```bash
docker ps
```

#### List all containers (including stopped)
```bash
docker ps -a
```

#### Check container status
```bash
docker ps --filter "name=doctor-consultation-service"
```

#### Stop the container
```bash
docker stop doctor-consultation-service
```

#### Start a stopped container
```bash
docker start doctor-consultation-service
```

#### Restart the container
```bash
docker restart doctor-consultation-service
```

#### Remove the container (must be stopped first)
```bash
docker rm doctor-consultation-service
```

#### Force remove a running container
```bash
docker rm -f doctor-consultation-service
```

### Logs

#### View logs
```bash
docker logs doctor-consultation-service
```

#### Follow logs in real-time
```bash
docker logs -f doctor-consultation-service
```

#### View last N lines of logs
```bash
docker logs --tail 100 doctor-consultation-service
```

#### View logs with timestamps
```bash
docker logs -t doctor-consultation-service
```

#### View logs since a specific time
```bash
docker logs --since 10m doctor-consultation-service
```

### Health Checks

#### Check container health status
```bash
docker inspect --format='{{.State.Health.Status}}' doctor-consultation-service
```

#### View detailed health check information
```bash
docker inspect doctor-consultation-service | grep -A 10 Health
```

### Executing Commands

#### Access container shell
```bash
docker exec -it doctor-consultation-service /bin/sh
```

#### Execute a command in the container
```bash
docker exec doctor-consultation-service ps aux
```

#### Check Java process
```bash
docker exec doctor-consultation-service ps aux | grep java
```

### Image Management

#### List images
```bash
docker images
```

#### Remove image
```bash
docker rmi doctor-consultation-service:latest
```

#### Remove image (force)
```bash
docker rmi -f doctor-consultation-service:latest
```

#### View image details
```bash
docker inspect doctor-consultation-service:latest
```

#### View image history
```bash
docker history doctor-consultation-service:latest
```

### Pushing to Docker Registries

You can push your Docker image to both public and private repositories. Here are instructions for various registries:

#### Docker Hub (Public Repository)

1. **Create a Docker Hub account** at [hub.docker.com](https://hub.docker.com)

2. **Login to Docker Hub:**
   ```bash
   docker login
   ```
   Enter your Docker Hub username and password when prompted.

3. **Tag your image** with your Docker Hub username:
   ```bash
   docker tag doctor-consultation-service:latest YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:latest
   ```
   Example:
   ```bash
   docker tag doctor-consultation-service:latest bipulk202001215/doctor-consultation-service:latest
   ```

4. **Push to Docker Hub:**
   ```bash
   docker push YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:latest
   ```

5. **Push with version tag:**
   ```bash
   docker tag doctor-consultation-service:latest YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:v1.0.0
   docker push YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:v1.0.0
   ```

6. **Pull from Docker Hub (anyone can pull public images):**
   ```bash
   docker pull YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:latest
   ```

#### Docker Hub (Private Repository)

1. **Create a private repository** on Docker Hub:
   - Go to Docker Hub → Repositories → Create Repository
   - Set visibility to "Private"

2. **Tag and push** (same as public, but only you can pull):
   ```bash
   docker tag doctor-consultation-service:latest YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:latest
   docker push YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:latest
   ```

3. **Login required to pull private images:**
   ```bash
   docker login
   docker pull YOUR_DOCKERHUB_USERNAME/doctor-consultation-service:latest
   ```

#### GitHub Container Registry (ghcr.io)

1. **Create a GitHub Personal Access Token** with `write:packages` permission

2. **Login to GitHub Container Registry:**
   ```bash
   echo $GITHUB_TOKEN | docker login ghcr.io -u YOUR_GITHUB_USERNAME --password-stdin
   ```
   Or:
   ```bash
   docker login ghcr.io -u YOUR_GITHUB_USERNAME
   ```

3. **Tag your image:**
   ```bash
   docker tag doctor-consultation-service:latest ghcr.io/YOUR_GITHUB_USERNAME/doctor-consultation-service:latest
   ```

4. **Push to GitHub Container Registry:**
   ```bash
   docker push ghcr.io/YOUR_GITHUB_USERNAME/doctor-consultation-service:latest
   ```

5. **Make it public (optional):**
   - Go to GitHub → Your repository → Packages → Package settings → Change visibility

#### AWS Elastic Container Registry (ECR)

1. **Install AWS CLI** and configure credentials:
   ```bash
   aws configure
   ```

2. **Create an ECR repository:**
   ```bash
   aws ecr create-repository --repository-name doctor-consultation-service --region us-east-1
   ```

3. **Get login token and login:**
   ```bash
   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
   ```

4. **Tag your image:**
   ```bash
   docker tag doctor-consultation-service:latest YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/doctor-consultation-service:latest
   ```

5. **Push to ECR:**
   ```bash
   docker push YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/doctor-consultation-service:latest
   ```

#### Google Container Registry (GCR)

1. **Install Google Cloud SDK** and authenticate:
   ```bash
   gcloud auth login
   gcloud auth configure-docker
   ```

2. **Tag your image:**
   ```bash
   docker tag doctor-consultation-service:latest gcr.io/YOUR_PROJECT_ID/doctor-consultation-service:latest
   ```

3. **Push to GCR:**
   ```bash
   docker push gcr.io/YOUR_PROJECT_ID/doctor-consultation-service:latest
   ```

#### Azure Container Registry (ACR)

1. **Login to Azure:**
   ```bash
   az login
   ```

2. **Login to ACR:**
   ```bash
   az acr login --name YOUR_REGISTRY_NAME
   ```

3. **Tag your image:**
   ```bash
   docker tag doctor-consultation-service:latest YOUR_REGISTRY_NAME.azurecr.io/doctor-consultation-service:latest
   ```

4. **Push to ACR:**
   ```bash
   docker push YOUR_REGISTRY_NAME.azurecr.io/doctor-consultation-service:latest
   ```

#### Quick Push Script

Create a script to automate the push process:

```bash
#!/bin/bash
# push-to-dockerhub.sh

DOCKERHUB_USERNAME="YOUR_DOCKERHUB_USERNAME"
IMAGE_NAME="doctor-consultation-service"
VERSION=${1:-latest}

# Build the image
docker build -f backend-modules/doctor-consultation-service/Dockerfile -t ${IMAGE_NAME}:${VERSION} .

# Tag for Docker Hub
docker tag ${IMAGE_NAME}:${VERSION} ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${VERSION}
docker tag ${IMAGE_NAME}:${VERSION} ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest

# Login (if not already logged in)
docker login

# Push both tags
docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${VERSION}
docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest

echo "Successfully pushed ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${VERSION}"
```

Usage:
```bash
chmod +x push-to-dockerhub.sh
./push-to-dockerhub.sh v1.0.0
```

#### Best Practices

1. **Use semantic versioning:**
   ```bash
   docker tag doctor-consultation-service:latest your-registry/doctor-consultation-service:1.0.0
   docker tag doctor-consultation-service:latest your-registry/doctor-consultation-service:1.0
   docker tag doctor-consultation-service:latest your-registry/doctor-consultation-service:latest
   ```

2. **Never push sensitive data:**
   - Don't include passwords, API keys, or secrets in images
   - Use environment variables or secrets management

3. **Use multi-stage builds** (already implemented in Dockerfile)

4. **Scan images for vulnerabilities:**
   ```bash
   docker scan doctor-consultation-service:latest
   ```

5. **Use .dockerignore** (already created) to exclude unnecessary files

### Networking

#### View container network settings
```bash
docker inspect doctor-consultation-service | grep -A 20 NetworkSettings
```

#### Test connectivity from host
```bash
curl http://localhost:8080
```

#### Test connectivity from another container
```bash
docker run --rm --network container:doctor-consultation-service curlimages/curl http://localhost:8080
```

### Resource Usage

#### View container resource usage
```bash
docker stats doctor-consultation-service
```

#### View container resource usage (one-time)
```bash
docker stats --no-stream doctor-consultation-service
```

### Cleanup

#### Stop and remove container
```bash
docker stop doctor-consultation-service && docker rm doctor-consultation-service
```

#### Remove unused images
```bash
docker image prune
```

#### Remove all unused resources
```bash
docker system prune
```

#### Remove everything (including volumes)
```bash
docker system prune -a --volumes
```

## Using Colima (Alternative to Docker Desktop)

If you're using Colima instead of Docker Desktop:

### Start Colima
```bash
colima start
```

### Stop Colima
```bash
colima stop
```

### Check Colima status
```bash
colima status
```

### View Colima logs
```bash
colima logs
```

## Environment Variables

The following environment variables can be set when running the container:

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Spring profile to use | `default` |
| `JAVA_OPTS` | JVM options | `-Xms512m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0` |
| `SPRING_DATA_MONGODB_URI` | MongoDB connection URI | From `application.properties` |
| `SERVER_PORT` | Server port | `8080` |

## Troubleshooting

### Container won't start

1. Check logs:
   ```bash
   docker logs doctor-consultation-service
   ```

2. Check if port is already in use:
   ```bash
   lsof -i :8080
   ```

3. Try running in foreground to see errors:
   ```bash
   docker run -p 8080:8080 doctor-consultation-service:latest
   ```

### Application can't connect to MongoDB

1. Verify MongoDB URI is correct in environment variables or `application.properties`
2. Check network connectivity from container:
   ```bash
   docker exec doctor-consultation-service ping -c 3 doctors-consultation.qiqf4rt.mongodb.net
   ```

### Container keeps restarting

1. Check exit code:
   ```bash
   docker inspect doctor-consultation-service | grep ExitCode
   ```

2. View logs for errors:
   ```bash
   docker logs doctor-consultation-service
   ```

### Health check failing

1. Check if application is responding:
   ```bash
   curl http://localhost:8080
   ```

2. Verify port is exposed correctly:
   ```bash
   docker port doctor-consultation-service
   ```

## Docker Compose (Optional)

You can also use Docker Compose to manage the service. Create a `docker-compose.yml`:

```yaml
version: '3.8'

services:
  doctor-consultation-service:
    build:
      context: ../../
      dockerfile: backend-modules/doctor-consultation-service/Dockerfile
    container_name: doctor-consultation-service
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=default
      - JAVA_OPTS=-Xms512m -Xmx1024m
    healthcheck:
      test: ["CMD", "nc", "-z", "localhost", "8080"]
      interval: 30s
      timeout: 3s
      retries: 3
      start_period: 40s
    restart: unless-stopped
```

Then use:
```bash
# Build and start
docker-compose up -d

# View logs
docker-compose logs -f

# Stop
docker-compose down
```

## Useful Scripts

### Quick restart script
```bash
#!/bin/bash
docker stop doctor-consultation-service
docker rm doctor-consultation-service
docker build -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:latest .
docker run -d -p 8080:8080 --name doctor-consultation-service doctor-consultation-service:latest
```

### Clean rebuild script
```bash
#!/bin/bash
docker stop doctor-consultation-service 2>/dev/null
docker rm doctor-consultation-service 2>/dev/null
docker rmi doctor-consultation-service:latest 2>/dev/null
docker build --no-cache -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:latest .
docker run -d -p 8080:8080 --name doctor-consultation-service doctor-consultation-service:latest
docker logs -f doctor-consultation-service
```

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Colima Documentation](https://github.com/abiosoft/colima)


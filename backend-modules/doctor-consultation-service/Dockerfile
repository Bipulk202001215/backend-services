# syntax=docker/dockerfile:1
#
# Dockerfile for Doctor Consultation Service
# 
# Build from the project root directory:
#   docker build -f backend-modules/doctor-consultation-service/Dockerfile -t doctor-consultation-service:latest .
#
# Run the container:
#   docker run -p 8080:8080 doctor-consultation-service:latest
#
# Run with custom environment variables:
#   docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod -e JAVA_OPTS="-Xms1g -Xmx2g" doctor-consultation-service:latest

# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy the root pom.xml first
COPY pom.xml .

# Copy all pom.xml files to leverage Docker layer caching
COPY backend-modules/pom.xml backend-modules/pom.xml
COPY backend-modules/common-utils/pom.xml backend-modules/common-utils/pom.xml
COPY backend-modules/login-service/pom.xml backend-modules/login-service/pom.xml
COPY backend-modules/payment-service/pom.xml backend-modules/payment-service/pom.xml
COPY backend-modules/doctor-consultation-service/pom.xml backend-modules/doctor-consultation-service/pom.xml

# Download dependencies (this layer will be cached if pom.xml files don't change)
RUN mvn --no-transfer-progress -pl backend-modules/doctor-consultation-service -am dependency:go-offline || true

# Copy the full source code
COPY backend-modules backend-modules

# Build the application (skip tests for faster builds, can be enabled in CI/CD)
RUN mvn --no-transfer-progress -pl backend-modules/doctor-consultation-service -am clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy AS runtime

# Install netcat for health checks
RUN apt-get update && apt-get install -y --no-install-recommends netcat-openbsd && \
    rm -rf /var/lib/apt/lists/*

# Create a non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=default
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Copy the built JAR from builder stage
COPY --from=builder /workspace/backend-modules/doctor-consultation-service/target/doctor-consultation-service-*.jar app.jar

# Change ownership to non-root user
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Expose the application port
EXPOSE 8080

# Health check - checks if the port is listening
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD nc -z localhost 8080 || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]


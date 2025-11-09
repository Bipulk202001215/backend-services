# syntax=docker/dockerfile:1

FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy the Maven project definition files first to leverage Docker layer caching
COPY pom.xml .
COPY backend-modules/pom.xml backend-modules/pom.xml
COPY backend-modules/common-utils/pom.xml backend-modules/common-utils/pom.xml
COPY backend-modules/login-service/pom.xml backend-modules/login-service/pom.xml
COPY backend-modules/payment-service/pom.xml backend-modules/payment-service/pom.xml
COPY backend-modules/doctor-consultation-service/pom.xml backend-modules/doctor-consultation-service/pom.xml

# Download dependencies up front
RUN mvn --no-transfer-progress -pl backend-modules/doctor-consultation-service -am dependency:go-offline

# Copy the full source and build the doctor consultation service
COPY backend-modules backend-modules
RUN mvn --no-transfer-progress -pl backend-modules/doctor-consultation-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

# Default Spring profile can be overridden at runtime
ENV SPRING_PROFILES_ACTIVE=default

# Copy the built Spring Boot fat jar
COPY --from=builder /workspace/backend-modules/doctor-consultation-service/target/doctor-consultation-service-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


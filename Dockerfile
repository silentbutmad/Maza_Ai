# Stage 1: Build stage using Maven Wrapper
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy Maven wrapper and configuration
COPY .mvn/ .mvn
COPY mvnw mvnw.cmd pom.xml ./
RUN chmod +x mvnw

# Copy source code and package application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Render assigns dynamic PORT env variable
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

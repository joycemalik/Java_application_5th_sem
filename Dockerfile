# Dockerfile for Railway Deployment (Alternative to Maven)
# Use this if you prefer Docker-based deployment

FROM eclipse-temurin:11-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apk add --no-cache maven

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/rental-system-1.0.0.jar app.jar

# Expose port (Railway will override with PORT env var)
EXPOSE 5000

# Run the application
CMD ["java", "-jar", "app.jar"]

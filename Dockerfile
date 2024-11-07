# Use a base image with JDK
FROM openjdk:17-jdk-slim as build

# Set working directory
WORKDIR /app

# Copy the JAR file from your target folder
COPY target/creditmanagement.jar /app/creditmanagement.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/creditmanagement.jar"]

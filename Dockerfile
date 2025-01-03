# Build stage
FROM bellsoft/liberica-runtime-container:jdk-17-stream-musl as build
WORKDIR /build

# Copy Maven configuration files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn/
COPY mvnw.cmd .

# Download dependencies (to leverage Docker caching)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src /build/src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM bellsoft/liberica-runtime-container:jre-17-musl as runtime
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /build/target/creditmanagement.jar creditmanagement.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "creditmanagement.jar"]

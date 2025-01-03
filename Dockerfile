# Source: https://medium.com/@cat.edelveis/a-guide-to-docker-multi-stage-builds-for-spring-boot-08e3a64c9812

############### Build stage ###############
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

############### Optimisation stage ###############
FROM bellsoft/liberica-runtime-container:jdk-17-stream-musl as optimiser
WORKDIR /optimiser

# Copy the built JAR file from the build stage
COPY --from=build /build/target/creditmanagement.jar creditmanagement.jar

# Extract the layers and the launcher script
RUN java -Djarmode=tools -jar creditmanagement.jar extract --layers --launcher

############### Runtime stage ###############
FROM bellsoft/liberica-runtime-container:jre-17-musl as runtime
WORKDIR /app

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
COPY --from=optimiser /optimiser/creditmanagement/dependencies/ ./
COPY --from=optimiser /optimiser/creditmanagement/spring-boot-loader/ ./
COPY --from=optimiser /optimiser/creditmanagement/snapshot-dependencies/ ./
COPY --from=optimiser /optimiser/creditmanagement/application/ ./

# Expose the application's port
EXPOSE 8080


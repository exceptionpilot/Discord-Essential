# Use the official maven/Java 8 image to create a build artifact.
FROM maven:3.8.1-openjdk-17-slim AS build

# Set the working directory in the image to /app
WORKDIR /discord

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application
COPY src /discord/src

# Build the application
RUN mvn package -DskipTests

# Use openjdk for runtime
FROM openjdk:17-alpine

# Set the working directory in the image to /app
WORKDIR /discord

# Copy the jar file from the build stage
COPY --from=build /discord/target/*.jar build.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build.jar"]
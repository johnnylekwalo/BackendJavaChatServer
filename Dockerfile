# Use the official OpenJDK base image for Java 17
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/BackendJavaChatServer.jar /app/BackendJavaChatServer.jar

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "BackendJavaChatServer.jar"]
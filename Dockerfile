# Use the official OpenJDK 17 image as the base image
FROM openjdk:17

# Set the working directory within the container
WORKDIR /app

COPY build/libs/best-commerce-api-0.0.1-SNAPSHOT.jar /app/best-commerce-api.jar

# Expose the port 8083
EXPOSE 8083

# Start the Spring Boot application when the container starts
CMD ["java", "-jar", "best-commerce-api.jar"]

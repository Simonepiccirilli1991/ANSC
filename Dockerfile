FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/ANSC0-0.0.1-SNAPSHOT.jar /app/ANSC0.jar

# Expose the port
EXPOSE 8081
  
LABEL name="ansc-img"

# Run the application
CMD ["java", "-jar", "ANSC0.jar"]

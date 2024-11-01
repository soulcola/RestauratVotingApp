# Start with a base image containing Java runtime
FROM openjdk:22-jdk-slim

# Add Maintainer Info
LABEL maintainer="8441404@gmail.com"

# Make port 8880 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/restauratvotingapp-1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
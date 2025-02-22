FROM openjdk:17-jdk-slim

COPY target/restauratvotingapp-1.0.jar /app.jar

# Make port 8880 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]

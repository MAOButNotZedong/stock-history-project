# First stage: build the application
FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

# Second stage: create a slim image
FROM amazoncorretto:17.0.14-alpine3.21
LABEL version="1.0"
COPY --from=build /app/target/final-project-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

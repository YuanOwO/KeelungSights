# 1. Build Stage
FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

# 2. Run Stage
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar ./app.jar
EXPOSE 5050
ENTRYPOINT ["java", "-jar", "app.jar"]

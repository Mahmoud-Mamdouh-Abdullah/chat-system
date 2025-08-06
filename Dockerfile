FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/chat-app-*.jar /app/chat-app.jar

EXPOSE 8080
CMD ["java", "-jar", "chat-app.jar"]
FROM maven AS build

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/chat-app-*.jar /app/chat-app.jar

EXPOSE 8080
CMD ["java", "-jar", "chat-app.jar"]
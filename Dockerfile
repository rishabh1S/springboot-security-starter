FROM maven:3.9.9-amazoncorretto-17-al2023 AS build
COPY pom.xml .
COPY src ./src 
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/springapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]


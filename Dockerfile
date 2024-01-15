FROM maven:3.9.6-eclipse-temurin-17-alpine as build

ENV BUILD_HOME=/app

WORKDIR $BUILD_HOME

COPY . .

RUN [ "mvn", "clean", "package", "-DskipTests" ]


FROM openjdk:17-jdk-alpine3.14

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

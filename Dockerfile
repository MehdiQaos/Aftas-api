FROM openjdk:17

WORKDIR /app

COPY ./target/Aftas-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "Aftas-0.0.1-SNAPSHOT.jar"]

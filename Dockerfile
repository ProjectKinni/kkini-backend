FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /build/libs/kinni-backend-0.0.1-SNAPSHOT.jar .
ENTRYPOINT java -jar kinni-backend-0.0.1-SNAPSHOT.jar
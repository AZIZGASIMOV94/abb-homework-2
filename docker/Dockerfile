FROM openjdk:8-jdk-alpine
ADD . /docker
WORKDIR /docker
EXPOSE 8081:8081
ENTRYPOINT ./mvnw spring-boot:run

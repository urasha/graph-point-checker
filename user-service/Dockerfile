FROM openjdk:17-jdk-slim

WORKDIR /opt/project

COPY ./user-service ./

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/target/userservice-3.4.1.jar"]
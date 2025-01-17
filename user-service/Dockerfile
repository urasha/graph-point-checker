FROM openjdk:17-jdk-slim

WORKDIR /opt/project/user-service

COPY ./user-service ./

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/user-service/target/userservice-3.4.1.jar"]
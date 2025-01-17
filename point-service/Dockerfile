FROM openjdk:17-jdk-slim

WORKDIR /opt/project/point-service

COPY ./point-service ./

EXPOSE 7070

CMD ["java", "-jar", "/opt/project/point-service/target/pointservice-3.4.1.jar"]
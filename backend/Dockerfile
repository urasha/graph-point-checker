FROM openjdk:17-jdk-slim

WORKDIR /opt/project

COPY ./backend ./

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/target/webspring-3.4.1.jar"]
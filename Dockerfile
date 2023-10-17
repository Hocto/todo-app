FROM openjdk:11-jdk-slim
COPY target/todo-app-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","application.jar"]
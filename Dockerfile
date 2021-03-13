FROM openjdk:13-jdk-alpine3.9
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
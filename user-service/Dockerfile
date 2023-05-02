FROM openjdk:17
ADD target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "user-service.jar"]
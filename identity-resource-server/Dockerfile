FROM openjdk:17
ADD target/identity-service-0.0.1-SNAPSHOT.jar identity-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "identity-service.jar"]
FROM openjdk:17
ADD target/transaction-service-0.0.1-SNAPSHOT.jar transaction-service.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]

#docker run -p 3306:3306 --name mysql-standalone -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=account-db -e MYSQL_USER=admin -e MYSQL_PASSWORD=root -d mysql:5.6

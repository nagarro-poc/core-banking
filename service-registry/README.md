## service-registry
Eureka Server is an application that holds the information about all client-service applications. Every Micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address.

Implementing a Eureka Server for service registry(Code Implementation).
1. adding spring-cloud-starter-netflix-eureka-server to the dependencies, below dependency is required.
```
   <dependencies>
    <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-server -->
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>

  </dependencies>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
```
2. You first need a Eureka Service registry. You can use Spring Cloud’s @EnableEurekaServer to stand up a registry with which other applications can communicate. This is a regular Spring Boot application with one annotation (@EnableEurekaServer) added to enable the service registry
```   
   @SpringBootApplication
   @EnableEurekaServer
   public class ServiceRegistryApplication {
      public static void main(String[] args){
         SpringApplication.run(ServiceRegistryApplication.class, args);
      }
   }
```
3. configuring properties. Define the application.yml as follows
```
server:
  port: 8761
eureka:
  server:
    hostname: service-registry
  client:
    fetch-registry: false
    register-with-eureka: false
spring:
  application:
    name: service-registry
```
   
Implementing a Eureka client(a regular Spring Boot application) :

1. adding spring-cloud-starter-netflix-eureka-server to the dependencies, below dependency is required.

```    
   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
```

2. configuring properties. Define the application.yml as follows
```
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
    fetch-registry: true
    register-with-eureka: true
```

Now you can start both the application and access the eureka server dashboard with url http://localhost:8761
here you can check all the service registered with Eureka server.
---

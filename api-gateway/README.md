## api-gateway
An API gateway is programming that sits in front of an API and is the single-entry point for defined back-end APIs and microservices. Sitting in front of APIs, the gateway acts as protection, administering security and scalability, and high availability. To put it simply, API Gateway takes all API requests from a customer, determines which services are demanded, and combines them into a unified, flawless experience for users.

Spring Cloud API Gateway Implementation(Code Implementation):-

1. adding spring-cloud-starter-gateway to the dependencies, below dependency is required.
```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
```


2. configuring properties. Define the application.yml as follows
```agsl
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://user-service:8081/
          predicates:
            - Path=/api/v1/user/**
        - id: account-service
          uri: http://account-service:8086/
          predicates:
            - Path=/api/v1/account/**
```
---
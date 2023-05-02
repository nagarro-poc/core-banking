# **Core banking microservice application**


### **Technology**
*    Java 17
*    Spring Boot 3.0.5
*    Maven 3.9.1


## Architecture
![img.png](img.png)
1. user-service
2. account-service
3. notification-service
4. transaction-service
5. api-gateway
6. service-registry
7. identity-service
8. cloud config

---

---
| Tables               | Are  | to-do |
|----------------------|:----:|------:|
| service-registry     | 8761 |       |
| api-gateway          | 8090 |       |
| user-service         | 8092 |       |
| identity-gateway     | 8082 |       |
| account-service      | 8086 |       |
| notification-service | 8088 |       |
| transaction-service  | 8087 |       |
| config server        | 8888 |       |  

---

## Steps to Setup

1. Clone the application

    ```git clone https://github.com/nagarro-poc/core-banking.git```
    ```git clone https://github.com/nagarro-poc/config-repo.git```
2. build the project

    ```mvn clean package```
3. build docker images

   ```docker-compose build```
4. start the container

    ```docker-compose up```
5. to stop the container
   
    ```docker-compose down```


## Explore Rest APIs

Open API Specification - Version 3.0.3 using Swagger

1. Swagger for User services:
   ```http://localhost:8081/swagger-ui/index.html```
2. Swagger for Account services:
   ```http://localhost:8086/swagger-ui/index.html```
3. Swagger for Identity services:
   ```http://localhost:8082/swagger-ui/index.html```

[Postman Collection
]
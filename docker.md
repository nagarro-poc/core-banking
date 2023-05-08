docker run --name service-registry -p 8761:8761 --network pre-defined-images_core-network nagarropoc/service-registry:latest

Run docker container after running the pre-defined images-

docker run -v /nagarro/logs/service-registry:/logs --name service-registry -p 8761:8761 -d --network pre-defined-images_core-network nagarropoc/service-registry:latest

docker run -v /nagarro/logs/api-gateway:/logs --name api-gateway -p 8090:8090 -d --network pre-defined-images_core-network nagarropoc/api-gateway:latest

docker run -v /nagarro/logs/account-service:/logs --name account-service -p 8086:8086 -d --network pre-defined-images_core-network nagarropoc/account-service:latest

docker run -v /nagarro/logs/config-server:/logs --name config-server -p 8888:8888 -d --network pre-defined-images_core-network nagarropoc/config-server:latest 

docker run -v /nagarro/logs/user-service:/logs --name user-service -p 8092:8092 -d --network pre-defined-images_core-network nagarropoc/user-service:latest 

docker run -v /nagarro/logs/identity-service:/logs --name identity-service -p 8082:8082 -d --network pre-defined-images_core-network nagarropoc/identity-service:latest 

docker run -v /nagarro/logs/transaction-service:/logs --name transaction-service -p 8087:8087 -d --network pre-defined-images_core-network nagarropoc/transaction-service:latest 

docker run -v /nagarro/logs/notification-service:/logs --name notification-service -p 8088:8088 -d --network pre-defined-images_core-network nagarropoc/notification-service:latest



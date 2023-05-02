docker run --name service-registry -p 8761:8761 --network pre-defined-images_core-network nagarropoc/service-registry:latest

Run docker container after running the pre-defined images-

docker run --name service-registry -p 8761:8761 --network pre-defined-images_core-network nagarropoc/service-registry:latest
docker run --name api-gateway -p 8090:8090 -d --network pre-defined-images_core-network nagarropoc/api-gateway:latest
docker run --name config-server -p 8888:8888 -d  --network pre-defined-images_core-network nagarropoc/config-server:latest
docker run --name user-service -p 8092:8092 -d --network pre-defined-images_core-network nagarropoc/user-service:latest
docker run --name account-service -p 8086:8086 -d --network pre-defined-images_core-network nagarropoc/account-service:latest
docker run --name identity-service -p 8082:8082 -d --network pre-defined-images_core-network nagarropoc/identity-service:latest
docker run --name transaction-service -p 8087:8087 -d --network pre-defined-images_core-network nagarropoc/transaction-service:latest
docker run --name notification-service -p 8088:8088 -d --network pre-defined-images_core-network nagarropoc/notification-service:latest


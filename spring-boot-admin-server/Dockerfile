#docker build -t <name>:<version> -f ./Dockerfile .
#docker run --name <name> -d -p <port>:8080 <name:version>
FROM openjdk:21-ea-17-jdk
WORKDIR /app
COPY ./build/libs/spring-boot-admin-server-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
CMD java -jar ./spring-boot-admin-server-0.0.1-SNAPSHOT.jar
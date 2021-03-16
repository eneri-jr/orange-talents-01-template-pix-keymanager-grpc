# Rodando a imagem
FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=build/libs/*all.jar
COPY ${JAR_FILE} keymanager-grpc-0.1-runner.jar
ENTRYPOINT ["java","-jar","/keymanager-grpc-0.1-runner.jar"]

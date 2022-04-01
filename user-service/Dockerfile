FROM maven:3.6.3 AS maven
LABEL MAINTAINER="htyesilyurt@gmail.com"

WORKDIR /opt/user-service
COPY . /opt/user-service
RUN mvn clean install 

# For Java 11, 
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=user-service.jar

WORKDIR /opt/user-service

COPY --from=maven /opt/user-service/target/${JAR_FILE} /opt/user-service/

ENTRYPOINT ["java","-jar","user-service.jar"]
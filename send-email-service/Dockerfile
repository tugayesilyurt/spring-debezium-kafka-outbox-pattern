FROM maven:3.6.3 AS maven
LABEL MAINTAINER="htyesilyurt@gmail.com"

WORKDIR /opt/send-email-service
COPY . /opt/send-email-service
RUN mvn clean install 

# For Java 11, 
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=send-email-service.jar

WORKDIR /opt/send-email-service

COPY --from=maven /opt/send-email-service/target/${JAR_FILE} /opt/send-email-service/

ENTRYPOINT ["java","-jar","send-email-service.jar"]
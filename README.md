## Docker Compose Spring Boot,Debezium,Apache Kafka with outbox-pattern ##

## System Design ##

- **System Design**
![System Design](https://github.com/tugayesilyurt/spring-debezium-kafka-outbox-pattern/blob/main/assets/system-design.PNG)

## Follow the steps for building and installing

#### 1: Build the `custom-debezium-connect` component.

```shell
cd debezium-transformer
mvn clean install
docker build -t custom-debezium-connect .
```

#### 2: Run the Docker-Compose

```shell
docker-compose up -d
```

#### 3: Linking the Debezium Kafka Connect With the Outbox Table

```shell
curl -X POST \
  http://localhost:8083/connectors/ \
  -H 'content-type: application/json' \
  -d '{
   "name": "user-outbox-connector",
   "config": {
      "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
      "tasks.max": "1",
      "database.hostname": "postgres",
      "database.port": "5432",
      "database.user": "tugayesilyurt",
      "database.password": "123456",
      "database.dbname": "userdb",
      "database.server.name": "pg-outbox-server",
      "tombstones.on.delete": "false",
      "table.whitelist": "public.outbox",
      "transforms": "outbox",
      "transforms.outbox.type": "com.transform.CustomTransformation"
   }
}
```
#### 4: Don't forget the change gmail account

```shell
@Configuration
public class GmailConfig {

    @Bean("gmail")
    public JavaMailSender gmailMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("CHANGE_ME");
        mailSender.setPassword("CHANGE_ME");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
```

#### 5: Starting the `user-service`

```shell
cd user-service
mvn spring-boot:run
```

#### 5: Starting the `send-email-service`

```shell
cd send-email-service
mvn spring-boot:run
```

### EndPoints ###

| Service       | EndPoint                      | Method | Description                                      |
| ------------- | ----------------------------- | :-----:| ------------------------------------------------ |
| Create User   | http://localhost:8080/v1/user | POST   | Create User 	            	                    |



- **Debezium Connector**

![Debezium Connector](https://github.com/tugayesilyurt/spring-debezium-kafka-outbox-pattern/blob/main/assets/debezium-connector.PNG)

- **Kafdrop**

![Kafdrop](https://github.com/tugayesilyurt/spring-debezium-kafka-outbox-pattern/blob/main/assets/user-created-kafka.PNG)

- **Welcome Message**

![Kafdrop](https://github.com/tugayesilyurt/spring-debezium-kafka-outbox-pattern/blob/main/assets/welcome-message.PNG)


package com.send.email.service;

import java.util.LinkedHashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.send.email.message.UserCreatedMessage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class SendEmailService {

	private final ObjectMapper objectMapper;
	
	@Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;

	@KafkaListener(topics = "user-created", containerFactory = "kafkaListenerContainerFactoryUser", groupId = "consumerFactoryUser")
	public void consumer(@Payload ConsumerRecord<?, ?> message,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.OFFSET) String offset)
			throws JsonMappingException, JsonProcessingException {

		log.info(message.value().toString());

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> messageMap = (LinkedHashMap<String, String>) message.value();

		String payload = messageMap.get("payload");

		UserCreatedMessage userCreatedMessage = objectMapper.readValue(payload, UserCreatedMessage.class);
		
		sendWelcomeEmail(userCreatedMessage);

	}

	private void sendWelcomeEmail(UserCreatedMessage userCreatedMessage) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(userCreatedMessage.getEmail());

		msg.setSubject("Welcome message!");
		msg.setText("User Created for username : " + userCreatedMessage.getUsername());

		mailSender.send(msg);

	}

}
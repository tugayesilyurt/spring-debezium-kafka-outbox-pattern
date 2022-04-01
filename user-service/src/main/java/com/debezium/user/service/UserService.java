package com.debezium.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.debezium.user.dto.UserCreatedJson;
import com.debezium.user.entity.User;
import com.debezium.user.outbox.EventPublisher;
import com.debezium.user.outbox.entity.OutboxEvent;
import com.debezium.user.repository.UserRepository;
import com.debezium.user.request.CreateUserRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final EventPublisher eventPublisher;

	public void createUser(CreateUserRequest request) {

		log.info("Create User for username : {}", request.getUsername());

		User user = userRepository.save(User.builder().username(request.getUsername()).password(request.getPassword())
				.email(request.getEmail()).build());
		
		eventPublisher.fire(createEnrollEvent(user));

	}
	
    public static OutboxEvent createEnrollEvent(User user) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(UserCreatedJson.builder().username(user.getUsername()).email(user.getEmail()).build(), JsonNode.class);

        return new OutboxEvent(
                user.getUserId(),
                "user-created",
                jsonNode
        );
    }

}

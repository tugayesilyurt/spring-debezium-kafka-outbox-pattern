package com.debezium.user.outbox;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.debezium.user.outbox.entity.Outbox;
import com.debezium.user.outbox.entity.OutboxEvent;
import com.debezium.user.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

	private final OutboxRepository outboxRepository;

	@EventListener
	public void handleOutboxEvent(OutboxEvent event) {

		UUID uuid = UUID.randomUUID();
		Outbox entity = new Outbox(uuid, LocalDateTime.now(), event.getAggregateId(), event.getEventType(),
				event.getPayload().toString());

		log.info("Handling event : {}.", entity);

		outboxRepository.save(entity);

	}
}

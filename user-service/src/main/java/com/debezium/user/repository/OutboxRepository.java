package com.debezium.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.debezium.user.outbox.entity.Outbox;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Integer> {

}

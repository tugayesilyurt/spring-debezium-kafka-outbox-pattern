package com.debezium.user.outbox.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OUTBOX")
@Getter
@Setter
public class Outbox {

    @Id
    @Column(name = "uuid")
    private UUID uuid;
    
    @Column(name = "createdDate")
    private LocalDateTime createdDate;
    
    @Column(name = "aggregateId")
    private Integer aggregateId;

    @Column(name = "eventType")
    private String eventType;

    @Column(name = "payload")
    private String payload;

   
}

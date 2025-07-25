package br.com.rinha.quarkus.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@DynamicUpdate
public class Payment {

    @Id
    @Column(name = "correlation_id")
    private UUID correlationId;
    private BigDecimal amount;
    private Status status;
    private Processor processor;
    @Column(name = "requested_at")
    private Instant requestedAt;

    public Payment(UUID correlationId, BigDecimal amount, Instant instant) {
        this.correlationId = correlationId;
        this.amount = amount;
        this.status = Status.PENDING;
        this.requestedAt = instant;
    }

    @Deprecated
    public Payment() {

    }

}

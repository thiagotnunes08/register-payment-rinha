package br.com.rinha.quarkus.dto;

import br.com.rinha.quarkus.entities.Payment;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(@NotNull UUID correlationId, @NotNull BigDecimal amount) {

    public Payment toModel(UUID correlationId, BigDecimal amount) {
        return new Payment(correlationId,amount);
    }
}

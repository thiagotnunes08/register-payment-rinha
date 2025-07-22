package br.com.rinha.quarkus.dto;

import br.com.rinha.quarkus.entities.Processor;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
@RegisterForReflection
public record PaymentSummaryDTO(Long totalRequests, BigDecimal totalAmount, Processor processorType) {
}

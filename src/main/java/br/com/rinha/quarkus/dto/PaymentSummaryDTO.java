package br.com.rinha.quarkus.dto;

import br.com.rinha.quarkus.entities.Processor;

import java.math.BigDecimal;

public record PaymentSummaryDTO(Long totalRequests, BigDecimal totalAmount, Processor processorType) {
}

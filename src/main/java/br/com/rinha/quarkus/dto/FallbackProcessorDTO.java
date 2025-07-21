package br.com.rinha.quarkus.dto;

import java.math.BigDecimal;

public record FallbackProcessorDTO(Long totalRequests, BigDecimal totalAmount) {
}

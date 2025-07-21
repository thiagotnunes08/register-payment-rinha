package br.com.rinha.quarkus.dto;

import java.math.BigDecimal;

public record DefaultProcessorDTO(Long totalRequests, BigDecimal totalAmount) {
}

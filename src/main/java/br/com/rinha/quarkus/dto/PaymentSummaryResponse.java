package br.com.rinha.quarkus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentSummaryResponse(@JsonProperty("default") DefaultProcessorDTO defaullt, FallbackProcessorDTO fallback) {

}
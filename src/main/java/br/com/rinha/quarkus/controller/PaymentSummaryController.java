package br.com.rinha.quarkus.controller;


import br.com.rinha.quarkus.dto.DefaultProcessorDTO;
import br.com.rinha.quarkus.dto.FallbackProcessorDTO;
import br.com.rinha.quarkus.dto.PaymentSummaryResponse;
import br.com.rinha.quarkus.entities.Processor;
import br.com.rinha.quarkus.entities.Status;
import br.com.rinha.quarkus.repositories.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Path("/payments-summary")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentSummaryController {

    @Inject
    PaymentRepository paymentRepository;

    @GET
    public PaymentSummaryResponse getSummary(@QueryParam("from") String from,
                                             @QueryParam("to") String to) {

        Instant fromInstant = null;
        Instant toInstant = null;

        if (from != null && to != null) {
            fromInstant = Instant.parse(from);
            toInstant = Instant.parse(to);
        }

        if (from == null && to == null) {
            fromInstant = Instant.now().minus(1, ChronoUnit.HOURS);
            toInstant = Instant.now();
        } else if (from == null) {
            fromInstant = Instant.now().minus(1, ChronoUnit.HOURS);
        } else if (to == null) {
            toInstant = Instant.now();
        }

        var payments = paymentRepository.getPaymentSummaryBetween(Status.PAID, fromInstant, toInstant);

        return switch (payments.size()) {
            case 0 -> new PaymentSummaryResponse(
                    new DefaultProcessorDTO(0L, BigDecimal.ZERO),
                    new FallbackProcessorDTO(0L, BigDecimal.ZERO)
            );

            case 1 -> {
                var p = payments.getFirst();
                yield switch (p.processorType()) {
                    case Processor.DEFAULT -> new PaymentSummaryResponse(
                            new DefaultProcessorDTO(p.totalRequests(), p.totalAmount()),
                            new FallbackProcessorDTO(0L, BigDecimal.ZERO)
                    );
                    case Processor.FALLBACK -> new PaymentSummaryResponse(
                            new DefaultProcessorDTO(0L, BigDecimal.ZERO),
                            new FallbackProcessorDTO(p.totalRequests(), p.totalAmount())
                    );
                };
            }

            default -> {
                var defaultt = payments.get(0);
                var fallback = payments.get(1);
                yield new PaymentSummaryResponse(
                        new DefaultProcessorDTO(defaultt.totalRequests(), defaultt.totalAmount()),
                        new FallbackProcessorDTO(fallback.totalRequests(), fallback.totalAmount())
                );
            }
        };
    }
}

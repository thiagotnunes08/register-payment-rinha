package br.com.rinha.quarkus.controller;


import br.com.rinha.quarkus.dto.PaymentRequest;
import br.com.rinha.quarkus.repositories.PaymentRepository;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import java.time.Clock;

@Path("/payments")
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentRegisterController {

    @Inject
    PaymentRepository paymentRepository;

    private final Clock clock = Clock.systemUTC();


    @POST
    @RunOnVirtualThread
    public void processPayment(@Valid PaymentRequest payment) {
        var newPayment = payment.toModel(payment.correlationId(), payment.amount(),clock.instant());
        paymentRepository.persist(newPayment);
    }
}

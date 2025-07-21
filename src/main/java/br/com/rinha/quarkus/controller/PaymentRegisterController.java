package br.com.rinha.quarkus.controller;


import br.com.rinha.quarkus.dto.PaymentRequest;
import br.com.rinha.quarkus.repositories.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/payments")
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentRegisterController {

    @Inject
    PaymentRepository paymentRepository;

    @POST
    @Transactional
    public void processPayment(@Valid PaymentRequest payment) {
        var newPayment = payment.toModel(payment.correlationId(), payment.amount());
        paymentRepository.persist(newPayment);
    }
}

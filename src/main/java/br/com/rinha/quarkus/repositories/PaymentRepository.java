package br.com.rinha.quarkus.repositories;

import br.com.rinha.quarkus.dto.PaymentSummaryDTO;
import br.com.rinha.quarkus.entities.Payment;
import br.com.rinha.quarkus.entities.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
@Transactional
public class PaymentRepository implements PanacheRepository<Payment> {

    public List<PaymentSummaryDTO> getPaymentSummaryBetween(Status status, Instant from, Instant to) {
        return getEntityManager().createQuery("""
            select new br.com.rinha.quarkus.dto.PaymentSummaryDTO(
                count(p), sum(p.amount), p.processor
            )
            from Payment p
            where p.status = :status
              and p.requestedAt between :from and :to
            group by p.processor
            order by p.processor asc
        """, PaymentSummaryDTO.class)
                .setParameter("status", status)
                .setParameter("from", from)
                .setParameter("to", to)
                .setLockMode(LockModeType.WRITE)
                .getResultList();
    }
}

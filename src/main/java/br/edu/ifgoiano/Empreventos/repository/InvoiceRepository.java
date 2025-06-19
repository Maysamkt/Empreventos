package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Invoice;
import br.edu.ifgoiano.Empreventos.model.Invoice.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);

    List<Invoice> findByStatusAndDueDateBefore(InvoiceStatus status, LocalDate dueDate);

    @Query("SELECT i FROM Invoice i WHERE i.userPlan.user.id = :userId")
    List<Invoice> findByUserId(Long userId);

    @Query("SELECT i FROM Invoice i WHERE i.userPlan.id = :userPlanId")
    List<Invoice> findByUserPlanId(Long userPlanId);

    @Query("SELECT i FROM Invoice i WHERE i.dueDate BETWEEN :startDate AND :endDate")
    List<Invoice> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
}

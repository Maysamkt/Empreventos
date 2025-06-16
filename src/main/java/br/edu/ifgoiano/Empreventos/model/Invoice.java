package br.edu.ifgoiano.Empreventos.model;

import br.edu.ifgoiano.Empreventos.util.InvoiceStatus;
import br.edu.ifgoiano.Empreventos.util.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_plan_id", nullable = false)
    private UserPlan userPlan;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issue_date;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime due_date;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod payment_method;

    @Column(name = "payment_date")
    private LocalDateTime payment_date;

    @Column(name = "gateway_id")
    private String gateway_id;

    @Column(name = "billing_cycle")
    private String billing_cycle;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.valueOf(0.0);

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public Invoice () {}

    public Invoice(Long id, UserPlan userPlan, LocalDateTime issue_date, LocalDateTime due_date, BigDecimal amount, InvoiceStatus status) {
        this.id = id;
        this.userPlan = userPlan;
        this.issue_date = issue_date;
        this.due_date = due_date;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    public LocalDateTime getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(LocalDateTime issue_date) {
        this.issue_date = issue_date;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public String getGateway_id() {
        return gateway_id;
    }

    public void setGateway_id(String gateway_id) {
        this.gateway_id = gateway_id;
    }

    public String getBilling_cycle() {
        return billing_cycle;
    }

    public void setBilling_cycle(String billing_cycle) {
        this.billing_cycle = billing_cycle;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", userPlan=" + (userPlan != null ? userPlan.getId() : "null") + // Mostra o ID do UserPlan
                ", issue_date=" + issue_date +
                ", due_date=" + due_date +
                ", amount=" + amount +
                ", status=" + status +
                ", payment_method=" + payment_method +
                '}';
    }
}

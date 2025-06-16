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
@IdClass(Invoice.InvoiceId.class)
public class Invoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"Invoice"})
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    @JsonIgnoreProperties("Invoice")
    private Plan plan;


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

    public Invoice(Long id, User user, Plan plan, LocalDateTime issue_date, LocalDateTime due_date, BigDecimal amount, InvoiceStatus status, PaymentMethod payment_method, LocalDateTime payment_date, String gateway_id, String billing_cycle, BigDecimal discount, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.id = id;
        this.user = user;
        this.plan = plan;
        this.issue_date = issue_date;
        this.due_date = due_date;
        this.amount = amount;
        this.status = status;
        this.payment_method = payment_method;
        this.payment_date = payment_date;
        this.gateway_id = gateway_id;
        this.billing_cycle = billing_cycle;
        this.discount = discount;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
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
        return Objects.equals(id, invoice.id) && Objects.equals(user, invoice.user) && Objects.equals(plan, invoice.plan) && Objects.equals(issue_date, invoice.issue_date) && Objects.equals(due_date, invoice.due_date) && Objects.equals(amount, invoice.amount) && status == invoice.status && payment_method == invoice.payment_method && Objects.equals(payment_date, invoice.payment_date) && Objects.equals(gateway_id, invoice.gateway_id) && Objects.equals(billing_cycle, invoice.billing_cycle) && Objects.equals(discount, invoice.discount) && Objects.equals(created_at, invoice.created_at) && Objects.equals(updated_at, invoice.updated_at) && Objects.equals(deleted_at, invoice.deleted_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, plan, issue_date, due_date, amount, status, payment_method, payment_date, gateway_id, billing_cycle, discount, created_at, updated_at, deleted_at);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", user=" + user +
                ", plan=" + plan +
                ", issue_date=" + issue_date +
                ", due_date=" + due_date +
                ", amount=" + amount +
                ", status=" + status +
                ", payment_method=" + payment_method +
                ", payment_date=" + payment_date +
                ", gateway_id='" + gateway_id + '\'' +
                ", billing_cycle='" + billing_cycle + '\'' +
                ", discount=" + discount +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }

    public static class InvoiceId implements Serializable {
        private Long user;
        private Long plan;

        public InvoiceId() {
        }

        public InvoiceId(Long user, Byte role) {
            this.user = user;
            this.plan = plan;
        }
    }
}

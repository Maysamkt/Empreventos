package br.edu.ifgoiano.Empreventos.model;


import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_plan_id", nullable = false)
    private UserPlan userPlan;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PENDING', 'PAID', 'OVERDUE', 'CANCELED')")
    private InvoiceStatus status = InvoiceStatus.PENDENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", columnDefinition = "ENUM('CREDIT_CARD', 'PIX', 'BANK_SLIP', 'TRANSFER')")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "gateway_id", length = 255)
    private String gatewayId;

    @Column(name = "billing_cycle", nullable = false, length = 20)
    private String billingCycle;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updated_at;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date deleted_at;

    @PrePersist
    protected void onCreate() {
        created_at = new java.util.Date();
        updated_at = new java.util.Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new java.util.Date();
    }

    // Enums para status e método de pagamento
    public enum InvoiceStatus {
        PENDENTE("PENDING"),
        PAGO("PAID"),
        ATRASADO("OVERDUE"),
        CANCELADO("CANCELED");

        private final String valorBanco;

        InvoiceStatus(String valorBanco) {
            this.valorBanco = valorBanco;
        }

        public String getValorBanco() {
            return valorBanco;
        }

        @JsonValue
        public String getDescricao() {
            return this.name(); // Retorna o nome do enum em português
        }

        public static InvoiceStatus fromValorBanco(String valorBanco) {
            for (InvoiceStatus status : values()) {
                if (status.valorBanco.equalsIgnoreCase(valorBanco)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Status inválido: " + valorBanco);
        }
    }

    public enum PaymentMethod {
        CARTAO_CREDITO("CREDIT_CARD"),
        PIX("PIX"),
        BOLETO("BANK_SLIP"),
        TRANSFERENCIA("TRANSFER");

        private final String valorBanco;

        PaymentMethod(String valorBanco) {
            this.valorBanco = valorBanco;
        }

        public String getValorBanco() {
            return valorBanco;
        }

        @JsonValue
        public String getDescricao() {
            return this.name(); // Retorna o nome do enum em português
        }

        public static PaymentMethod fromValorBanco(String valorBanco) {
            for (PaymentMethod metodo : values()) {
                if (metodo.valorBanco.equalsIgnoreCase(valorBanco)) {
                    return metodo;
                }
            }
            throw new IllegalArgumentException("Método de pagamento inválido: " + valorBanco);
        }
    }

    public Invoice () {}

    public Invoice(Long id, UserPlan userPlan, LocalDate issueDate, LocalDate dueDate, BigDecimal amount, InvoiceStatus status, PaymentMethod paymentMethod, LocalDate paymentDate, String gatewayId, String billingCycle, BigDecimal discount, Date created_at, Date updated_at, Date deleted_at) {
        this.id = id;
        this.userPlan = userPlan;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.gatewayId = gatewayId;
        this.billingCycle = billingCycle;
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

    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
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
                ", userPlan=" + userPlan +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", amount=" + amount +
                ", status=" + status +
                ", paymentMethod=" + paymentMethod +
                ", paymentDate=" + paymentDate +
                ", gatewayId='" + gatewayId + '\'' +
                ", billingCycle='" + billingCycle + '\'' +
                ", discount=" + discount +
                '}';
    }

}

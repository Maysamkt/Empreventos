package br.edu.ifgoiano.Empreventos.model;

import br.edu.ifgoiano.Empreventos.util.PlanType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "plan")
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 10)
    private PlanType type;

    @Column(name = "description")
    private String description;

    @Column (name = "price")
    private BigDecimal price;

    @Column (name = "event_limit")
    private int event_limit;

    @Column (name = "max_listeners")
    private int max_listeners;

    @Column (name = "storage_mb")
    private int storage_mb;

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

    @OneToOne(mappedBy = "plan")
    private UserPlan userPlan;

    public Plan () {}

    public Plan(Long id, PlanType type, String description, BigDecimal price, int event_limit, int max_listeners, int storage_mb, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.event_limit = event_limit;
        this.max_listeners = max_listeners;
        this.storage_mb = storage_mb;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return event_limit == plan.event_limit && max_listeners == plan.max_listeners && storage_mb == plan.storage_mb && Objects.equals(id, plan.id) && type == plan.type && Objects.equals(description, plan.description) && Objects.equals(price, plan.price) && Objects.equals(created_at, plan.created_at) && Objects.equals(updated_at, plan.updated_at) && Objects.equals(deleted_at, plan.deleted_at) && Objects.equals(userPlan, plan.userPlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description, price, event_limit, max_listeners, storage_mb, created_at, updated_at, deleted_at, userPlan);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getEvent_limit() {
        return event_limit;
    }

    public void setEvent_limit(int event_limit) {
        this.event_limit = event_limit;
    }

    public int getMax_listeners() {
        return max_listeners;
    }

    public void setMax_listeners(int max_listeners) {
        this.max_listeners = max_listeners;
    }

    public int getStorage_mb() {
        return storage_mb;
    }

    public void setStorage_mb(int storage_mb) {
        this.storage_mb = storage_mb;
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

    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }
}

package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_plan")
public class UserPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @MapsId
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime start_date;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime end_date;

    @Column(name = "last_payment", nullable = false)
    private LocalDateTime last_payment;

    @Column(name = "next_payment", nullable = false)
    private LocalDateTime next_payment;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public UserPlan () {}

    public UserPlan(Long id, Long userId, Long planId, User user, Plan plan, LocalDateTime start_date, LocalDateTime end_date, LocalDateTime last_payment, LocalDateTime next_payment, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.id = id;
        this.userId = userId;
        this.planId = planId;
        this.user = user;
        this.plan = plan;
        this.start_date = start_date;
        this.end_date = end_date;
        this.last_payment = last_payment;
        this.next_payment = next_payment;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public LocalDateTime getLast_payment() {
        return last_payment;
    }

    public void setLast_payment(LocalDateTime last_payment) {
        this.last_payment = last_payment;
    }

    public LocalDateTime getNext_payment() {
        return next_payment;
    }

    public void setNext_payment(LocalDateTime next_payment) {
        this.next_payment = next_payment;
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
        UserPlan userPlan = (UserPlan) o;
        return Objects.equals(id, userPlan.id) && Objects.equals(userId, userPlan.userId) && Objects.equals(planId, userPlan.planId) && Objects.equals(user, userPlan.user) && Objects.equals(plan, userPlan.plan) && Objects.equals(start_date, userPlan.start_date) && Objects.equals(end_date, userPlan.end_date) && Objects.equals(last_payment, userPlan.last_payment) && Objects.equals(next_payment, userPlan.next_payment) && Objects.equals(created_at, userPlan.created_at) && Objects.equals(updated_at, userPlan.updated_at) && Objects.equals(deleted_at, userPlan.deleted_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, planId, user, plan, start_date, end_date, last_payment, next_payment, created_at, updated_at, deleted_at);
    }
}

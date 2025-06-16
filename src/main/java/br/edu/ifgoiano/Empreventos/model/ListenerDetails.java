package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "listener_details")
public class ListenerDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long user_id;

    @OneToOne
    @MapsId //compartilha a mesma chave primaria
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String company;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
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

    public ListenerDetails() {}
    public ListenerDetails(Long user_id, User user, String company, String position, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.user_id = user_id;
        this.user = user;
        this.company = company;
        this.position = position;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        ListenerDetails that = (ListenerDetails) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(user, that.user) && Objects.equals(company, that.company) && Objects.equals(position, that.position) && Objects.equals(created_at, that.created_at) && Objects.equals(updated_at, that.updated_at) && Objects.equals(deleted_at, that.deleted_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user, company, position, created_at, updated_at, deleted_at);
    }

    @Override
    public String toString() {
        return "ListenerDetails{" +
                "user_id=" + user_id +
                ", user=" + user +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}

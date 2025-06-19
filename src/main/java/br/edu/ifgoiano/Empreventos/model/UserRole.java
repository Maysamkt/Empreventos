package br.edu.ifgoiano.Empreventos.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_role")
@IdClass(UserRole.UserRoleId.class)
public class UserRole {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"userRoles", "speakerDetails", "listenerDetails"})
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties("userRoles")
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    // Classe para a chave primária composta
    public static class UserRoleId implements Serializable {
        private Long user;
        private Byte role;

        public UserRoleId() {
        }

        public UserRoleId(Long user, Byte role) {
            this.user = user;
            this.role = role;
        }

        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user = user;
        }

        public Byte getRole() {
            return role;
        }

        public void setRole(Byte role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoleId that = (UserRoleId) o;
            return Objects.equals(user, that.user) &&
                    Objects.equals(role, that.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, role);
        }
    }

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    public String toString() {
        return "UserRole{" +
                "user=" + user +
                ", role=" + role +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}
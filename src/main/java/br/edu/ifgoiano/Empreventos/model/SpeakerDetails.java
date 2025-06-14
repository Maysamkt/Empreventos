package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "speaker_details")
public class SpeakerDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long user_id;

    @OneToOne
    @MapsId //compartilha a mesma chave primaria
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String resume;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(nullable = false, length = 255)
    private String linkedin;

    @Column(name = "other_social_networks", length = 255)
    private String other_social_networks;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deleted_at;

    @PrePersist //para atualizacao automatica
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate //para atualizacao automatica
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public SpeakerDetails() {}
    public SpeakerDetails(Long user_id, User user, String resume, String specialization, String linkedin, String other_social_networks, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.user_id = user_id;
        this.user = user;
        this.resume = resume;
        this.specialization = specialization;
        this.linkedin = linkedin;
        this.other_social_networks = other_social_networks;
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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getOther_social_networks() {
        return other_social_networks;
    }

    public void setOther_social_networks(String other_social_networks) {
        this.other_social_networks = other_social_networks;
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
        SpeakerDetails that = (SpeakerDetails) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(user, that.user) && Objects.equals(resume, that.resume) && Objects.equals(specialization, that.specialization) && Objects.equals(linkedin, that.linkedin) && Objects.equals(other_social_networks, that.other_social_networks) && Objects.equals(created_at, that.created_at) && Objects.equals(updated_at, that.updated_at) && Objects.equals(deleted_at, that.deleted_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user, resume, specialization, linkedin, other_social_networks, created_at, updated_at, deleted_at);
    }

    @Override
    public String toString() {
        return "SpeakerDetails{" +
                "user_id=" + user_id +
                ", user=" + user +
                ", resume='" + resume + '\'' +
                ", specialization='" + specialization + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", other_social_networks='" + other_social_networks + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}

package br.edu.ifgoiano.Empreventos.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(length = 20, unique = true)
    private String cpf_cnpj;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column(nullable = false, length = 20)
    private String phone_number;
    @Column(nullable = false)
    private Boolean active;
    @Column(length = 255)
    private String avatar_url;
    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_at;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private SpeakerDetails speakerDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ListenerDetails listenerDetails;

    //@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //private OrganizerDetails organizerDetails;

    // Métodos utilitários
    public void addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        userRoles.add(userRole);
    }


    public void removeRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        userRoles.remove(userRole);
    }

    public void setSpeakerDetails(SpeakerDetails details) {
        this.speakerDetails = details;
        if (details != null) {
            details.setUser(this);
        }
    }

    public void setListenerDetails(ListenerDetails details) {
        this.listenerDetails = details;
        if (details != null) {
            details.setUser(this);
        }
    }

//    public void setOrganizerDetails(OrganizerDetails details) {
//        this.organizerDetails = details;
//        if (details != null) {
//            details.setUser(this);
//        }
//    }

    public User() {
    }

    public User(Long id, String name, String password, String cpf_cnpj, String email, String phone_number, Boolean active, String avatar_url, String bio, Date created_at, Date updated_at, Date deleted_at, Set<UserRole> userRoles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.cpf_cnpj = cpf_cnpj;
        this.email = email;
        this.phone_number = phone_number;
        this.active = active;
        this.avatar_url = avatar_url;
        this.bio = bio;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(cpf_cnpj, user.cpf_cnpj) && Objects.equals(email, user.email) && Objects.equals(phone_number, user.phone_number) && Objects.equals(active, user.active) && Objects.equals(avatar_url, user.avatar_url) && Objects.equals(bio, user.bio) && Objects.equals(created_at, user.created_at) && Objects.equals(updated_at, user.updated_at) && Objects.equals(deleted_at, user.deleted_at) && Objects.equals(userRoles, user.userRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, cpf_cnpj, email, phone_number, active, avatar_url, bio, created_at, updated_at, deleted_at, userRoles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", cpf_cnpj='" + cpf_cnpj + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", active=" + active +
                ", avatar_url='" + avatar_url + '\'' +
                ", bio='" + bio + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                ", userRoles=" + userRoles +
                '}';
    }
}
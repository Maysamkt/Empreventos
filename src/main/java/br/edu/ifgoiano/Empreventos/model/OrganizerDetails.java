package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "organizer_details")
public class OrganizerDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "company_name", nullable = false, length = 100)
    private String company_name;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "industry_of_business", nullable = false, length = 100)
    private String industry_of_business;

    @Column(name = "website", nullable = false, length = 100)
    private String website;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @Column(name = "deleted_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deleted_at;

    @OneToMany(mappedBy = "organizerDetails", cascade = CascadeType.ALL)
    private List<Event> event;

    @OneToMany(mappedBy = "organizerDetails", cascade = CascadeType.ALL)
    private List<Invoice> invoice;

    @OneToOne(mappedBy = "organizerDetails")
    private Plan plan;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public OrganizerDetails() {}

    public OrganizerDetails(Long user_id, User user, String company_name, String brand, String industry_of_business, String website, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.user_id = user_id;
        this.user = user;
        this.company_name = company_name;
        this.brand = brand;
        this.industry_of_business = industry_of_business;
        this.website = website;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIndustry_of_business() {
        return industry_of_business;
    }

    public void setIndustry_of_business(String industry_of_business) {
        this.industry_of_business = industry_of_business;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
        OrganizerDetails that = (OrganizerDetails) o;
        return Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }

    @Override
    public String toString() {
        return "OrganizerDetails{" +
                "user_id=" + user_id +
                ", user=" + user +
                ", company_name='" + company_name + '\'' +
                ", brand='" + brand + '\'' +
                ", industry_of_business='" + industry_of_business + '\'' +
                ", website='" + website + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}


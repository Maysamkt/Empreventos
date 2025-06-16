package br.edu.ifgoiano.Empreventos.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrganizerDetailsResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long user_id;
    private String companyName;
    private String industryOfBusiness;
    private String website;
//    private List<Invoice> invoice;
//    private Plano plano;


    public OrganizerDetailsResponseDTO() {

    }

    public OrganizerDetailsResponseDTO(Long user_id, String companyName, String brand, String industryOfBusiness, String website, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.user_id = user_id;
        this.companyName = companyName;
        this.industryOfBusiness = industryOfBusiness;
        this.website = website;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustryOfBusiness() {
        return industryOfBusiness;
    }

    public void setIndustryOfBusiness(String industryOfBusiness) {
        this.industryOfBusiness = industryOfBusiness;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

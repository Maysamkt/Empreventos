package br.edu.ifgoiano.Empreventos.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class OrganizerDetailsRequestDTO {

    @NotBlank(message = "Razão Social da empresa não pode ficar em branco")
    private String companyName;
    @NotBlank(message = "O Nome fantasia/marca da empresa é obrigatório.")
    private String brand;
    @NotBlank(message = "A área de atuação da empresa é obrigatório.")
    private String industryOfBusiness;
    @NotBlank(message = "O link para o site da empresa deve ser informado")
    private String website;

    public OrganizerDetailsRequestDTO() {

    }

    public OrganizerDetailsRequestDTO( String companyName, String brand, String industryOfBusiness, String website, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {

        this.companyName = companyName;
        this.brand = brand;
        this.industryOfBusiness = industryOfBusiness;
        this.website = website;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

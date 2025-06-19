package br.edu.ifgoiano.Empreventos.dto.request;



import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class ListenerDetailsRequestDTO implements Serializable {

    @NotBlank(message = "O nome da empresa é obrigatório")
    private String company;
    @NotBlank(message = "Preencha seu cargo")
    private String position;

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
}

package br.edu.ifgoiano.Empreventos.dto.request;



import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class SpeakerDetailsRequestDTO implements Serializable {

    @NotBlank(message = "Seu currículo ou uma breve descrição da sua trajetória deve ser inserida.")
    private String resume;
    @NotBlank(message = "Informe sua área de atuação ou Especialização.")
    private String specialization;
    @NotBlank(message = "O link para seu perfil no linkedin não pode ficar em branco.")
    private String linkedin;
    private String other_social_networks;

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
}

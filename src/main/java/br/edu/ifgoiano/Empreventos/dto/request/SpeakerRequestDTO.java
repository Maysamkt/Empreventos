package br.edu.ifgoiano.Empreventos.dto.request;



import java.io.Serializable;

public class SpeakerRequestDTO implements Serializable {
    private String resume;
    private String specialization;
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

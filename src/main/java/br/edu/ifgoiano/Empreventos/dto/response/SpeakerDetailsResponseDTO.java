package br.edu.ifgoiano.Empreventos.dto.response;

public class SpeakerDetailsResponseDTO {

    private Long id;
    private String userName;
    private String resume;
    private String specialization;
    private String linkedin;
    private String other_social_networks;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}

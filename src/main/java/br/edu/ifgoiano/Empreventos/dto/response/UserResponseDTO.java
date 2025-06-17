package br.edu.ifgoiano.Empreventos.dto.response;



import java.io.Serializable;

import java.util.List;


public class UserResponseDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Boolean active;
    private String bio;
    private List<String> roles;
    private SpeakerDetailsResponseDTO speakerDetails;
    private ListenerDetailsResponseDTO listenerDetails;
    private OrganizerDetailsResponseDTO organizerDetails;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    public SpeakerDetailsResponseDTO getSpeakerDetails() {
        return speakerDetails;
    }

    public void setSpeakerDetails(SpeakerDetailsResponseDTO speakerDetails) {
        this.speakerDetails = speakerDetails;
    }

    public ListenerDetailsResponseDTO getListenerDetails() {
        return listenerDetails;
    }

    public void setListenerDetails(ListenerDetailsResponseDTO listenerDetails) {
        this.listenerDetails = listenerDetails;
    }

    public OrganizerDetailsResponseDTO getOrganizerDetails() {
        return organizerDetails;
    }

    public void setOrganizerDetails(OrganizerDetailsResponseDTO organizerDetails) {
        this.organizerDetails = organizerDetails;
    }
}

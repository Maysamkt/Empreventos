package br.edu.ifgoiano.Empreventos.dto.request;




import br.edu.ifgoiano.Empreventos.model.Role;

import java.io.Serializable;
import java.util.Set;

public class UserRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

   // @NotBlank(message = "O nome é obrigatório")
    private String name;


  //  @NotBlank(message = "A Senha é obrigatória")
  //  @Size(min = 6, max = 10, message = "A senha deve ter entre 6 e 10 caracteres")
    private String password;

  //  @NotBlank(message = "CPF/CNPJ é obrigatório")
  //  @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF deve ter 11 dígitos ou CNPJ 14 dígitos")
    private String cpf_cnpj;

  //  @NotBlank(message = "O email é obrigatório")
 //   @Email(message = "Formato de e-mail inválido")
    private String email;

 //   @NotBlank(message = "Telefone é obrigatório")
  //  @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$",
    //        message = "Formato de telefone inválido")
    private String phone_number;

    private Boolean active = true;

    private String avatar_url;

    private String bio;

   // @NotEmpty(message = "Pelo menos uma role deve ser especificada")
    private Set<Role.RoleName> roles;


    private SpeakerRequestDTO speakerDetails;
    private ListenerRequestDTO listenerDetails;
   // private OrganizerRequestDTO organizerDetails;

    public UserRequestDTO() {}

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

    public Set<Role.RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role.RoleName> roles) {
        this.roles = roles;
    }

    public SpeakerRequestDTO getSpeakerDetails() {
        return speakerDetails;
    }

    public void setSpeakerDetails(SpeakerRequestDTO speakerDetails) {
        this.speakerDetails = speakerDetails;
    }

    public ListenerRequestDTO getListenerDetails() {
        return listenerDetails;
    }

    public void setListenerDetails(ListenerRequestDTO listenerDetails) {
        this.listenerDetails = listenerDetails;
    }

//    public OrganizerRequestDTO getOrganizerDetails() {
//        return organizerDetails;
//    }
//
//    public void setOrganizerDetails(OrganizerRequestDTO organizerDetails) {
//        this.organizerDetails = organizerDetails;
//    }



}

package br.edu.ifgoiano.Empreventos.mapper;

import br.edu.ifgoiano.Empreventos.dto.request.UserRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.UserResponseDTO;
import br.edu.ifgoiano.Empreventos.model.ListenerDetails;
import br.edu.ifgoiano.Empreventos.model.OrganizerDetails;
import br.edu.ifgoiano.Empreventos.model.SpeakerDetails;
import br.edu.ifgoiano.Empreventos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final SpeakerDetailsMapper speakerDetailsMapper;
    private final ListenerDetailsMapper listenerDetailsMapper;
    private final OrganizerDetailsMapper organizerDetailsMapper;



    @Autowired
    public UserMapper(SpeakerDetailsMapper speakerDetailsMapper, ListenerDetailsMapper listenerDetailsMapper, OrganizerDetailsMapper organizerDetailsMapper) {
        this.speakerDetailsMapper = speakerDetailsMapper;
        this.listenerDetailsMapper = listenerDetailsMapper;
        this.organizerDetailsMapper = organizerDetailsMapper;
    }

    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhone_number());
        dto.setActive(user.getActive());
        dto.setBio(user.getBio());

        // Mapeia apenas os nomes das roles
        if(user.getUserRoles() != null) {
            dto.setRoles(user.getUserRoles().stream()
                    .map(ur -> ur.getRole() != null ? ur.getRole().getName().name() : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        } else {
            dto.setRoles(new ArrayList<>());
        }

        // Mapear detalhes da entidade para os DTOs de resposta dos detalhes
        if (user.getSpeakerDetails() != null) {
            dto.setSpeakerDetails(speakerDetailsMapper.toResponseDTO(user.getSpeakerDetails()));
        }
        if (user.getListenerDetails() != null) {
            dto.setListenerDetails(listenerDetailsMapper.toResponseDTO(user.getListenerDetails()));
        }
        if (user.getOrganizerDetails() != null) {
            dto.setOrganizerDetails(organizerDetailsMapper.toResponseDTO(user.getOrganizerDetails()));
        }




        return dto;
    }

    public User toEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCpf_cnpj(dto.getCpf_cnpj());
        user.setPhone_number(dto.getPhone_number());
        user.setActive(dto.getActive() != null ? dto.getActive() : true);
        user.setAvatar_url(dto.getAvatar_url());
        user.setBio(dto.getBio());
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());

        if (dto.getSpeakerDetails() != null) {
            SpeakerDetails speakerDetails = speakerDetailsMapper.toEntity(dto.getSpeakerDetails());
            user.setSpeakerDetails(speakerDetails);
        }
        if (dto.getListenerDetails() != null) {
            ListenerDetails listenerDetails = listenerDetailsMapper.toEntity(dto.getListenerDetails());
            user.setListenerDetails(listenerDetails);
        }

        if (dto.getOrganizerDetails() != null){
            OrganizerDetails organizerDetails = organizerDetailsMapper.toEntity(dto.getOrganizerDetails());
            user.setOrganizerDetails(organizerDetails);
        }

        return user;
    }

    public void updateEntityFromDTO(UserRequestDTO dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null ) entity.setName(dto.getName());
        if (dto.getPhone_number() != null) entity.setPhone_number(dto.getPhone_number());
        if (dto.getCpf_cnpj() != null) entity.setCpf_cnpj(dto.getCpf_cnpj());
        if (dto.getAvatar_url() != null ) entity.setAvatar_url(dto.getAvatar_url());
        if (dto.getBio() != null) entity.setBio(dto.getBio());
        entity.setUpdated_at(new Date());

        // Handle SpeakerDetails
        if (dto.getSpeakerDetails() != null) {
            if (entity.getSpeakerDetails() == null) {
                SpeakerDetails newSpeakerDetails = speakerDetailsMapper.toEntity(dto.getSpeakerDetails());
                newSpeakerDetails.setUser(entity);
                newSpeakerDetails.setUser_id(entity.getId());
                entity.setSpeakerDetails(newSpeakerDetails);
            } else {
                speakerDetailsMapper.updateEntityFromDTO(dto.getSpeakerDetails(), entity.getSpeakerDetails());
            }
        } else {

            if (entity.getSpeakerDetails() != null) {
                entity.setSpeakerDetails(null);
            }
        }
        // Handle ListenerDetails
        if (dto.getListenerDetails() != null) {
            if (entity.getListenerDetails() == null) {
                ListenerDetails newListenerDetails = listenerDetailsMapper.toEntity(dto.getListenerDetails());
                newListenerDetails.setUser(entity);
                newListenerDetails.setUser_id(entity.getId());
                entity.setListenerDetails(newListenerDetails);
            } else {
                listenerDetailsMapper.updateEntityFromDTO(dto.getListenerDetails(), entity.getListenerDetails());
            }
        } else {
            if (entity.getListenerDetails() != null) {
                entity.setListenerDetails(null);
            }
        }

        if (dto.getOrganizerDetails() != null) {
            if (entity.getOrganizerDetails() == null) {
                OrganizerDetails newOrganizerDetails = organizerDetailsMapper.toEntity(dto.getOrganizerDetails());
                newOrganizerDetails.setUser(entity); // Associa o User
                newOrganizerDetails.setUser_id(entity.getId()); // Define o user_id
                entity.setOrganizerDetails(newOrganizerDetails);
            } else {
                organizerDetailsMapper.updateEntityFromDTO(dto.getOrganizerDetails(), entity.getOrganizerDetails());
            }
        } else {
            if (entity.getOrganizerDetails() != null) {
                entity.setOrganizerDetails(null);
            }
        }
    }


}

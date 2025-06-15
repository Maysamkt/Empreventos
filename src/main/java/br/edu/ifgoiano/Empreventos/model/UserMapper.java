package br.edu.ifgoiano.Empreventos.model;

import br.edu.ifgoiano.Empreventos.dto.request.UserRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

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
        dto.setAvatarUrl(user.getAvatar_url());
        dto.setBio(user.getBio());

        // Mapeia apenas os nomes das roles
        if(user.getUserRoles() != null) {
            dto.setRoles(user.getUserRoles().stream()
                    .map(ur -> ur.getRole() != null ? ur.getRole().getName().name() : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
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

        // Nota: As roles são adicionadas separadamente no service
        // pois precisam ser buscadas no banco de dados

        return user;
    }

    public void updateEntityFromDTO(UserRequestDTO dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setPhone_number(dto.getPhone_number());
        entity.setAvatar_url(dto.getAvatar_url());
        entity.setBio(dto.getBio());
        entity.setUpdated_at(new Date());

        // Campos que não devem ser atualizados:
        // email, cpf_cnpj, password (devem ter endpoints específicos para atualização)
    }
}

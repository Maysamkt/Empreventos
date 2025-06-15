package br.edu.ifgoiano.Empreventos.mapper;

import br.edu.ifgoiano.Empreventos.dto.request.ListenerRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.ListenerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.model.ListenerDetails;
import org.springframework.stereotype.Component;

@Component
public class ListenerDetailsMapper {

    public ListenerDetailsResponseDTO toResponseDTO(ListenerDetails listenerDetails) {
        if (listenerDetails == null) {
            return null;
        }

        ListenerDetailsResponseDTO dto = new ListenerDetailsResponseDTO();
        dto.setId(listenerDetails.getUser_id());
        dto.setCompany(listenerDetails.getCompany());
        dto.setPosition(listenerDetails.getPosition());

        return dto;

    }

    public ListenerDetails toEntity(ListenerRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ListenerDetails listenerDetails = new ListenerDetails();
        listenerDetails.setCompany(dto.getCompany());
        listenerDetails.setPosition(dto.getPosition());
        return listenerDetails;
    }

    public void updateEntityFromDTO(ListenerRequestDTO dto, ListenerDetails entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCompany(dto.getCompany());
        entity.setPosition(dto.getPosition());

    }
}

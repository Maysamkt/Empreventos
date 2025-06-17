package br.edu.ifgoiano.Empreventos.mapper;

import br.edu.ifgoiano.Empreventos.dto.request.ListenerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.ListenerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.model.ListenerDetails;
import br.edu.ifgoiano.Empreventos.repository.ListenerDetailsRepository;
import org.springframework.stereotype.Component;

@Component
public class ListenerDetailsMapper {

    private final ListenerDetailsRepository listenerDetailsRepository;

    public ListenerDetailsMapper(ListenerDetailsRepository listenerDetailsRepository) {
        this.listenerDetailsRepository = listenerDetailsRepository;
    }

    public ListenerDetailsResponseDTO toResponseDTO(ListenerDetails listenerDetails) {
        if (listenerDetails == null) {
            return null;
        }

        ListenerDetailsResponseDTO dto = new ListenerDetailsResponseDTO();
        dto.setId(listenerDetails.getUser_id());
        dto.setCompany(listenerDetails.getCompany());
        dto.setPosition(listenerDetails.getPosition());

        if (listenerDetails.getUser() != null) {
            dto.setId(listenerDetails.getUser().getId());
            dto.setUserName(listenerDetails.getUser().getName());
        }

        return dto;

    }

    public ListenerDetails toEntity(ListenerDetailsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ListenerDetails listenerDetails = new ListenerDetails();
        listenerDetails.setCompany(dto.getCompany());
        listenerDetails.setPosition(dto.getPosition());
        return listenerDetails;
    }

    public void updateEntityFromDTO(ListenerDetailsRequestDTO dto, ListenerDetails entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCompany(dto.getCompany());
        entity.setPosition(dto.getPosition());

    }
}

package br.edu.ifgoiano.Empreventos.mapper;

import br.edu.ifgoiano.Empreventos.model.OrganizerDetails;
import org.springframework.stereotype.Component;

@Component
public class OganizerDetailsMapper {

    public OrganizerDetailsResponseDTO toResponseDTO(OrganizerDetails organizerDetails) {
        if (organizerDetails == null){
            return null;
        }

        OrganizerDetailsResponseDTO dto = new OrganizerDetailsResponseDTO();
        dto.set
    }
}

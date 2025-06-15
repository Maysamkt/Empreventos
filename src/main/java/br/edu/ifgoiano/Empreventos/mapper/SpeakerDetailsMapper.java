package br.edu.ifgoiano.Empreventos.mapper;


import br.edu.ifgoiano.Empreventos.dto.request.SpeakerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.SpeakerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.model.SpeakerDetails;
import org.springframework.stereotype.Component;

@Component
public class SpeakerDetailsMapper {

    public SpeakerDetailsResponseDTO toResponseDTO(SpeakerDetails speakerDetails){
        if(speakerDetails == null){
            return null;
        }
        SpeakerDetailsResponseDTO dto = new SpeakerDetailsResponseDTO();
        dto.setId(speakerDetails.getUser_id());
        dto.setResume(speakerDetails.getResume());
        dto.setSpecialization(speakerDetails.getSpecialization());
        dto.setLinkedin(speakerDetails.getLinkedin());
        dto.setOther_social_networks(speakerDetails.getOther_social_networks());

        if (speakerDetails.getUser() != null) {
            dto.setId(speakerDetails.getUser().getId());
            dto.setUserName(speakerDetails.getUser().getName());
        }

        return dto;

    }

    public SpeakerDetails toEntity(SpeakerDetailsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        SpeakerDetails speakerDetails = new SpeakerDetails();
        speakerDetails.setResume(dto.getResume());
        speakerDetails.setSpecialization(dto.getSpecialization());
        speakerDetails.setLinkedin(dto.getLinkedin());
        speakerDetails.setOther_social_networks(dto.getOther_social_networks());
        return speakerDetails;
    }

    public void updateEntityFromDTO(SpeakerDetailsRequestDTO dto, SpeakerDetails entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setResume(dto.getResume());
        entity.setSpecialization(dto.getSpecialization());
        entity.setLinkedin(dto.getLinkedin());
        entity.setOther_social_networks(dto.getOther_social_networks());
    }
}

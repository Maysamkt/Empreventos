package br.edu.ifgoiano.Empreventos.mapper;

import br.edu.ifgoiano.Empreventos.dto.request.OrganizerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.OrganizerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.model.OrganizerDetails;
import br.edu.ifgoiano.Empreventos.repository.OrganizerDetailsRepository;
import org.springframework.stereotype.Component;

@Component
public class OrganizerDetailsMapper {

    private final OrganizerDetailsRepository organizerDetailsRepository;

    public OrganizerDetailsMapper(OrganizerDetailsRepository organizerDetailsRepository) {
        this.organizerDetailsRepository = organizerDetailsRepository;
    }

    public OrganizerDetailsResponseDTO toResponseDTO(OrganizerDetails organizerDetails) {
        if (organizerDetails == null){
            return null;
        }

        OrganizerDetailsResponseDTO dto = new OrganizerDetailsResponseDTO();
        dto.setUser_id(organizerDetails.getUser_id());
        dto.setCompanyName(organizerDetails.getCompany_name());
        dto.setBrand(organizerDetails.getBrand());
        dto.setWebsite(organizerDetails.getWebsite());
        dto.setIndustryOfBusiness(organizerDetails.getIndustry_of_business());

        if (organizerDetails.getUser() != null) {
            dto.setUser_id(organizerDetails.getUser().getId());
        }

        return dto;


    }

    public OrganizerDetails toEntity(OrganizerDetailsRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        OrganizerDetails organizerDetails = new OrganizerDetails();
        organizerDetails.setCompany_name(dto.getCompanyName());
        organizerDetails.setBrand(dto.getBrand());
        organizerDetails.setWebsite(dto.getWebsite());
        organizerDetails.setIndustry_of_business(dto.getIndustryOfBusiness());
        return organizerDetails;
    }

    public void updateEntityFromDTO(OrganizerDetailsRequestDTO dto, OrganizerDetails entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCompany_name(dto.getCompanyName());
        entity.setBrand(dto.getBrand());
        entity.setWebsite(dto.getWebsite());
        entity.setIndustry_of_business(dto.getIndustryOfBusiness());
    }
}

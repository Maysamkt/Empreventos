package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.response.OrganizerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.OrganizerDetails;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import br.edu.ifgoiano.Empreventos.repository.OrganizerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifgoiano.Empreventos.dto.request.OrganizerDetailsRequestDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class OrganizerDetailsService {

    @Autowired
    private OrganizerDetailsRepository organizerDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    public OrganizerDetailsRequestDTO create (OrganizerDetailsRequestDTO organizerDetailsRequestDTO) {
        var user = userRepository.findById(organizerDetailsRequestDTO.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("Empresa organizadora não encontrada com o ID: " + organizerDetailsRequestDTO.getUser_id()));

        var organizerDetails = DataMapper.parseObject(organizerDetailsRequestDTO, OrganizerDetails.class);
        organizerDetails.setUser(user);

        var savedOrganizerDetails = organizerDetailsRepository.save(organizerDetails);
        return DataMapper.parseObject(organizerDetails, OrganizerDetailsRequestDTO.class);
    }

    public OrganizerDetailsResponseDTO findById(Long organizerDetailsId) {
        var organizerDetails = organizerDetailsRepository.findById(organizerDetailsId)
                .orElseThrow(() -> new NoSuchElementException("Empresa organizadora não encontrada com o ID: " + organizerDetailsId));
        return DataMapper.parseObject(organizerDetails, OrganizerDetailsResponseDTO.class);
    }

    public List<OrganizerDetailsResponseDTO> findByUserId(long userId) {
        var organizerDetails = organizerDetailsRepository.findByUser_id(userId);
        return DataMapper.parseListObjects(organizerDetails, OrganizerDetailsResponseDTO.class);
    }


    public void delete(long userId) {
        var organizerDetails = organizerDetailsRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Empresa com ID " + userId + " não encontrada."));
        organizerDetails.setDeleted_at(LocalDateTime.now());
        organizerDetailsRepository.save(organizerDetails);
    }

    public OrganizerDetailsRequestDTO update(long userId, OrganizerDetailsRequestDTO organizerDetailsRequestDTO) {
        var organizerDetails = organizerDetailsRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Atividade com ID " + userId + " não encontrada."));
        organizerDetails.setUser_id(organizerDetails.getUser_id());
        organizerDetails.setBrand(organizerDetails.getBrand());
        organizerDetails.setCompany_name(organizerDetails.getCompany_name());
        organizerDetails.setWebsite(organizerDetails.getWebsite());
        organizerDetails.setIndustry_of_business(organizerDetails.getIndustry_of_business());


        var savedOrganizerDetails = organizerDetailsRepository.save(organizerDetails);
        return DataMapper.parseObject(savedOrganizerDetails, OrganizerDetailsRequestDTO.class);
    }
}


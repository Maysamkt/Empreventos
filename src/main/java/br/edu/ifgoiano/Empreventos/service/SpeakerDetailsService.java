package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.request.SpeakerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.SpeakerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.SpeakerDetailsMapper;
import br.edu.ifgoiano.Empreventos.model.SpeakerDetails;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.repository.SpeakerDetailsRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SpeakerDetailsService {
    private final SpeakerDetailsRepository speakerDetailsRepository;
    private final UserRepository userRepository;
    private final SpeakerDetailsMapper speakerDetailsMapper;
    private final Logger logger = Logger.getLogger(SpeakerDetailsService.class.getName());

    @Autowired
    public SpeakerDetailsService(SpeakerDetailsRepository speakerDetailsRepository,
                                 UserRepository userRepository,
                                 SpeakerDetailsMapper speakerDetailsMapper) {
        this.speakerDetailsRepository = speakerDetailsRepository;
        this.userRepository = userRepository;
        this.speakerDetailsMapper = speakerDetailsMapper;
    }


    public List<SpeakerDetailsResponseDTO> findAll() {
        logger.info("Buscando todos os palestrantes");
        return speakerDetailsRepository.findAll().stream()
                .map(speakerDetailsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public SpeakerDetailsResponseDTO findById(Long id) {
        logger.info("Buscando detalhes do palestrante por ID: " + id);
        SpeakerDetails speakerDetails = speakerDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Detalhes de Palestrante com ID " + id + " não encontrados."));
        return speakerDetailsMapper.toResponseDTO(speakerDetails);
    }

    @Transactional
    public SpeakerDetailsResponseDTO create(Long userId, SpeakerDetailsRequestDTO speakerRequestDTO) {
        logger.info("Criando detalhes de speaker para o usuário ID: " + userId);
        // verifique se o usuário existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + userId + " não encontrado."));

        // Verifique se o usuário já possui detalhes de speaker
        if (user.getSpeakerDetails() != null) {
            throw new IllegalArgumentException("Usuário com ID " + userId + " já possui detalhes de Palestrante. Use o método de atualização.");
        }

        SpeakerDetails speakerDetails = speakerDetailsMapper.toEntity(speakerRequestDTO);
        speakerDetails.setUser_id(userId);
        speakerDetails.setUser(user);
        user.setSpeakerDetails(speakerDetails);

        SpeakerDetails savedSpeakerDetails = speakerDetailsRepository.save(speakerDetails);
        userRepository.save(user); // Salva o usuário para persistir a associação (se necessário)

        return speakerDetailsMapper.toResponseDTO(savedSpeakerDetails);
    }

    @Transactional
    public SpeakerDetailsResponseDTO update(Long id, SpeakerDetailsRequestDTO speakerRequestDTO) {
        logger.info("Atualizando detalhes de speaker para o ID: " + id);
        SpeakerDetails existingSpeakerDetails = speakerDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Detalhes de Speaker com ID " + id + " não encontrados."));

        speakerDetailsMapper.updateEntityFromDTO(speakerRequestDTO, existingSpeakerDetails);

        SpeakerDetails updatedSpeakerDetails = speakerDetailsRepository.save(existingSpeakerDetails);
        return speakerDetailsMapper.toResponseDTO(updatedSpeakerDetails);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deletando detalhes de speaker para o ID: " + id);
        SpeakerDetails speakerDetails = speakerDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Detalhes de Speaker com ID " + id + " não encontrados."));

        // Remove a associação do usuário para permitir a exclusão do SpeakerDetails via orphanRemoval
        User user = speakerDetails.getUser();
        if (user != null) {
            user.setSpeakerDetails(null);
            userRepository.save(user);
        }

        speakerDetailsRepository.delete(speakerDetails);
    }
}

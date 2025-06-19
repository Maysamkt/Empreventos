package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.request.ListenerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.ListenerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.ListenerDetailsMapper;
import br.edu.ifgoiano.Empreventos.model.ListenerDetails;
import br.edu.ifgoiano.Empreventos.model.SpeakerDetails;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.repository.ListenerDetailsRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ListenerDetailsService {

    private final ListenerDetailsRepository listenerDetailsRepository;
    private final UserRepository userRepository;
    private final ListenerDetailsMapper listenerDetailsMapper;
    private final Logger logger = Logger.getLogger(SpeakerDetailsService.class.getName());

    @Autowired
    public ListenerDetailsService(ListenerDetailsRepository listenerDetailsRepository,
                                  UserRepository userRepository,
                                  ListenerDetailsMapper listenerDetailsMapper) {
        this.listenerDetailsRepository = listenerDetailsRepository;
        this.userRepository = userRepository;
        this.listenerDetailsMapper = listenerDetailsMapper;
    }

    public List<ListenerDetailsResponseDTO> findAll() {
        logger.info("Buscando todos os participantes");
        return listenerDetailsRepository.findAll().stream()
                .map(listenerDetailsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ListenerDetailsResponseDTO findById(Long id) {
        logger.info("Buscando todos os participantes por ID: " + id);
        ListenerDetails listenerDetails = listenerDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Detalhes de Participante com ID " + id + " não encontrados."));
        return listenerDetailsMapper.toResponseDTO(listenerDetails);
    }

    @Transactional
    public ListenerDetailsResponseDTO create(Long userId, ListenerDetailsRequestDTO listenerRequestDTO) {
        logger.info("Buscando todos os participantes por ID: " + userId);

        // verifique se o usuário existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + userId + " não encontrado."));

        // Verifique se o usuário já possui detalhes de listener
        if (user.getListenerDetails() != null) {
            throw new IllegalArgumentException("Usuário com ID " + userId + " já possui detalhes de Participante. Use o método de atualização.");
        }

        ListenerDetails listenerDetails = listenerDetailsMapper.toEntity(listenerRequestDTO);
        listenerDetails.setUser_id(userId);
        listenerDetails.setUser(user);
        user.setListenerDetails(listenerDetails);

        ListenerDetails savedListenerDetails = listenerDetailsRepository.save(listenerDetails);
        userRepository.save(user);

        return listenerDetailsMapper.toResponseDTO(savedListenerDetails);
    }

    @Transactional
    public ListenerDetailsResponseDTO update(Long id, ListenerDetailsRequestDTO listenerRequestDTO) {
        logger.info("Atualizando todos os detalhes de participante por ID: " + id);
        ListenerDetails existingListenerDetails = listenerDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Detalhes de Participante com ID " + id + " não encontrados."));

        listenerDetailsMapper.updateEntityFromDTO(listenerRequestDTO, existingListenerDetails);

        ListenerDetails updateListenerDetails = listenerDetailsRepository.save(existingListenerDetails);
        return listenerDetailsMapper.toResponseDTO(updateListenerDetails);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deletando todos os detalhes de participante por ID: " + id);
        ListenerDetails listenerDetails =  listenerDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Detalhes do Participante com ID " + id + " não encontrados."));
        User user = listenerDetails.getUser();
        if (user != null) {
            user.setListenerDetails(null);
            userRepository.save(user);
        }

        listenerDetailsRepository.delete(listenerDetails);

    }

    public Long getUserIdByListenerDetailsId(Long listenerDetailsId) {
        return listenerDetailsRepository.findById(listenerDetailsId)
                .map(ListenerDetails::getUser)
                .map(User::getId)
                .orElse(null);
    }
}


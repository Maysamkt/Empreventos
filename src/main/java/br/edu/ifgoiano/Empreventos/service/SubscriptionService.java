package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.controller.RatingController;
import br.edu.ifgoiano.Empreventos.controller.SubscriptionController;
import br.edu.ifgoiano.Empreventos.dto.SubscriptionDTO;
import br.edu.ifgoiano.Empreventos.dto.SubscriptionResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Subscription;
import br.edu.ifgoiano.Empreventos.util.SubscriptionStatus;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import br.edu.ifgoiano.Empreventos.repository.SubscriptionRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository; // Você precisará deste repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public SubscriptionResponseDTO create(SubscriptionDTO subscriptionDTO) {
        var event = eventRepository.findById(subscriptionDTO.getEventId())
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com o ID: " + subscriptionDTO.getEventId()));
        // Busca o usuário (participante) pelo ID fornecido no DTO
        var listener = userRepository.findById(subscriptionDTO.getListenerId())
                .orElseThrow(() -> new NoSuchElementException("Usuário (participante) não encontrado com o ID: " + subscriptionDTO.getListenerId()));

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setListener(listener);
        subscription.setAmountPaid(subscriptionDTO.getAmountPaid());
        subscription.setStatus(SubscriptionStatus.PENDING);

        var savedSubscription = subscriptionRepository.save(subscription);
        return toResponseDTO(savedSubscription);
    }

    public SubscriptionResponseDTO findById(Long id) {
        var subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada com o ID: " + id));
        return toResponseDTO(subscription);
    }


    public SubscriptionResponseDTO cancel(Long id) {
        var subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada com o ID: " + id));
        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscription.setDeletedAt(LocalDateTime.now());

        var savedSubscription = subscriptionRepository.save(subscription);
        return toResponseDTO(savedSubscription);
    }


    // Método privado para mapear Entidade para ResponseDTO
    private SubscriptionResponseDTO toResponseDTO(Subscription subscription) {
        var dto = DataMapper.parseObject(subscription, SubscriptionResponseDTO.class);
        dto.setEventId(subscription.getEvent().getId());
        dto.setEventTitle(subscription.getEvent().getTitle());
        dto.setListenerId(subscription.getListener().getId());
        dto.setListenerName(subscription.getListener().getName());
        dto.setStatus(subscription.getStatus().name());

        // Adicionando links HATEOAS
        // Link para o próprio recurso (self)
        dto.add(linkTo(methodOn(SubscriptionController.class).findById(dto.getId())).withSelfRel());

        // Link para as avaliações da inscrição
        dto.add(linkTo(methodOn(RatingController.class)
                .findBySubscription(dto.getId()))
                .withRel("ratings"));

        return dto;
    }
}
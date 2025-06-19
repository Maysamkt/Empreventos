package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.controller.RatingController;
import br.edu.ifgoiano.Empreventos.controller.SubscriptionController;
import br.edu.ifgoiano.Empreventos.dto.SubscriptionDTO;
import br.edu.ifgoiano.Empreventos.dto.SubscriptionResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.Subscription;
import br.edu.ifgoiano.Empreventos.security.jwt.UserDetailsImpl;
import br.edu.ifgoiano.Empreventos.util.SubscriptionStatus;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import br.edu.ifgoiano.Empreventos.repository.SubscriptionRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository; // Você precisará deste repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    @Autowired
    private EventService eventService;


    @Transactional
    public SubscriptionResponseDTO create(SubscriptionDTO subscriptionDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var listener = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new NoSuchElementException("Usuário (participante) não encontrado."));
        var event = eventRepository.findById(subscriptionDTO.getEventId())
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com o ID: " + subscriptionDTO.getEventId()));

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setListener(listener);
        subscription.setAmountPaid(subscriptionDTO.getAmountPaid());
        subscription.setStatus(SubscriptionStatus.CONFIRMED);
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());

        var savedSubscription = subscriptionRepository.save(subscription);
        return toResponseDTO(savedSubscription);
    }

    public SubscriptionResponseDTO findById(Long id) {
        var subscription = subscriptionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada com o ID: " + id));
        return toResponseDTO(subscription);
    }


    public SubscriptionResponseDTO cancel(Long id) {
        var subscription = subscriptionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada com o ID: " + id));
        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscription.setDeletedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());

        var savedSubscription = subscriptionRepository.save(subscription);
        return toResponseDTO(savedSubscription);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponseDTO> findSubscriptionsByEventId(Long eventId) throws AccessDeniedException {
        // 1. Obter o usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = userDetails.getId();

        // 2. Encontrar o evento
        Event event = eventRepository.findByIdAndDeletedAtIsNull(eventId)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + eventId + " não encontrado ou deletado."));

        // 3. Verificar permissão: Somente o organizador do evento ou um ADMIN pode ver as inscrições

        if (!event.getOrganizer().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Acesso negado. Você não é o organizador deste evento e não tem permissão para ver suas inscrições.");
        }

        // 4. Buscar todas as inscrições ativas para este evento
        List<Subscription> subscriptions = subscriptionRepository.findByEventAndDeletedAtIsNull(event);

        // 5. Mapear para DTOs de resposta e adicionar links HATEOAS
        return subscriptions.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    // Método privado para mapear Entidade para ResponseDTO
    private SubscriptionResponseDTO toResponseDTO(Subscription subscription) {
        var dto = DataMapper.parseObject(subscription, SubscriptionResponseDTO.class);
        dto.setEventId(subscription.getEvent().getId());
        dto.setEventTitle(subscription.getEvent().getTitle());
        dto.setListenerId(subscription.getListener().getId());
        dto.setListenerName(subscription.getListener().getName());
        dto.setStatus(subscription.getStatus().name());

        //links HATEOAS
        // Link para o próprio recurso (self)
        dto.add(linkTo(methodOn(SubscriptionController.class).findById(dto.getId())).withSelfRel());

        // Link para as avaliações da inscrição
        dto.add(linkTo(methodOn(RatingController.class)
                .findBySubscription(dto.getId()))
                .withRel("ratings"));

        return dto;
    }
}
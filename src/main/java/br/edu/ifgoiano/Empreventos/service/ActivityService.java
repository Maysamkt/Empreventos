package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.ActivityDTO;
import br.edu.ifgoiano.Empreventos.dto.ActivityResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Activity;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.repository.ActivityRepository;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifgoiano.Empreventos.controller.ActivityController;
import br.edu.ifgoiano.Empreventos.controller.ComplementaryMaterialController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public ActivityDTO create (ActivityDTO activityDTO) {
        var event = eventRepository.findById(activityDTO.getEventId())
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com o ID: " + activityDTO.getEventId()));

        var activity = DataMapper.parseObject(activityDTO, Activity.class);
        activity.setEvent(event);
        if (activityDTO.getSpeakerId() != null) {
            User speaker = userRepository.findById(activityDTO.getSpeakerId())
                    .orElseThrow(() -> new NoSuchElementException("Palestrante com ID " + activityDTO.getSpeakerId() + " não encontrado."));
            activity.setSpeaker(speaker);
        }
        var savedActivity = activityRepository.save(activity);
        return DataMapper.parseObject(savedActivity, ActivityDTO.class);
    }


    public ActivityResponseDTO findById(Long activityId) {
        var activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NoSuchElementException("Activity não encontrada com o ID: " + activityId));

        var activityDTO = DataMapper.parseObject(activity, ActivityResponseDTO.class);
        activityDTO.add(linkTo(methodOn(ActivityController.class).findById(activityId)).withSelfRel());
        activityDTO.add(linkTo(methodOn(ComplementaryMaterialController.class).findMateriaisPorAtividade(activityId)).withRel("materials"));

        if (activity.getSpeaker() != null) {
            activityDTO.setSpeakerName(activity.getSpeaker().getName());
        }
        return activityDTO;
    }


    public List<ActivityResponseDTO> findByEventoId(Long eventId) {
        var activities = activityRepository.findByEventId(eventId);
        var activityDTOs = DataMapper.parseListObjects(activities, ActivityResponseDTO.class);

        for (ActivityResponseDTO dto : activityDTOs) {
            dto.add(linkTo(methodOn(ActivityController.class).findById(dto.getId())).withSelfRel());
            dto.add(linkTo(methodOn(ComplementaryMaterialController.class)
                    .findMateriaisPorAtividade(dto.getId()))
                    .withRel("materials"));
        }
        return activityDTOs;

    }


    public void delete(Long activityId) {
        var activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NoSuchElementException("Atividade com ID " + activityId + " não encontrada."));
        activity.setDeletedAt(LocalDateTime.now());
        activityRepository.save(activity);
    }

    public ActivityDTO update(Long activityId, ActivityDTO activityDTO) {
        var activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NoSuchElementException("Atividade com ID " + activityId + " não encontrada."));
        activity.setTitle(activityDTO.getTitle());
        activity.setDescription(activityDTO.getDescription());
        activity.setStartDateTime(activityDTO.getStartDateTime());
        activity.setEndDateTime(activityDTO.getEndDateTime());
        activity.setLocation(activityDTO.getLocation());
        activity.setHoursCertified(activityDTO.getHoursCertified());
        activity.setPublished(activityDTO.isPublished());

        var savedActivity = activityRepository.save(activity);
        return DataMapper.parseObject(savedActivity, ActivityDTO.class);
    }
}
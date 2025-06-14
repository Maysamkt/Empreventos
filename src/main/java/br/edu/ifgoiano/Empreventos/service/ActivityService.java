package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.ActivityDTO;
import br.edu.ifgoiano.Empreventos.dto.ActivityResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Activity;
import br.edu.ifgoiano.Empreventos.repository.ActivityRepository;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EventRepository eventRepository;


    public ActivityDTO create (ActivityDTO activityDTO) {
        var event = eventRepository.findById(activityDTO.getEventId())
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com o ID: " + activityDTO.getEventId()));

        var activity = DataMapper.parseObject(activityDTO, Activity.class);
        activity.setEvent(event);

        var savedActivity = activityRepository.save(activity);
        return DataMapper.parseObject(savedActivity, ActivityDTO.class);
    }

    public ActivityResponseDTO findById(Integer activityId) {
        var activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NoSuchElementException("Activity não encontrada com o ID: " + activityId));
        return DataMapper.parseObject(activity, ActivityResponseDTO.class);
    }

    public List<ActivityResponseDTO> findByEventoId(Integer eventId) {
        var activities = activityRepository.findByEventId(eventId);
        return DataMapper.parseListObjects(activities, ActivityResponseDTO.class);
    }


    public void delete(Integer activityId) {
        var activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NoSuchElementException("Atividade com ID " + activityId + " não encontrada."));
        activity.setDeletedAt(LocalDateTime.now());
        activityRepository.save(activity);
    }

    public ActivityDTO update(Integer activityId, ActivityDTO activityDTO) {
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
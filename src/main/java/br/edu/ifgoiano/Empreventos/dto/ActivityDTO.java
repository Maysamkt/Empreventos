package br.edu.ifgoiano.Empreventos.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime; // ALTERADO

public class ActivityDTO {
    private Long id;

    private Long eventId;

    @NotBlank(message = "O título da atividade é obrigatório.")
    private String title;
    private String description;

    @NotNull(message = "A data e hora de início são obrigatórias.")
    @Future(message = "A data de início deve ser no futuro.")
    private LocalDateTime startDateTime;

    @NotNull(message = "A data e hora de fim são obrigatórias.")
    @Future(message = "A data de fim deve ser no futuro.")
    private LocalDateTime endDateTime;

    private String location;
    private Long hoursCertified;
    private boolean isPublished;


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getHoursCertified() {
        return hoursCertified;
    }

    public void setHoursCertified(Long hoursCertified) {
        this.hoursCertified = hoursCertified;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }
}
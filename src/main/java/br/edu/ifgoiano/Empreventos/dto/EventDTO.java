package br.edu.ifgoiano.Empreventos.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventDTO {

    private Integer id;

    @NotBlank(message = "O título é obrigatório.")
    private String title;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser no presente ou futuro.")
    private LocalDateTime startDate;

    @NotNull(message = "A data de fim é obrigatória.")
    @FutureOrPresent(message = "A data de fim deve ser no presente ou futuro.")
    private LocalDateTime endDate;

    @NotBlank(message = "O local é obrigatório.")
    private String location;

    @PositiveOrZero(message = "A capacidade deve ser um número positivo ou zero.")
    private int capacity;
    private String status;
    private BigDecimal registrationValue;

    public EventDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getRegistrationValue() {
        return registrationValue;
    }

    public void setRegistrationValue(BigDecimal registrationValue) {
        this.registrationValue = registrationValue;
    }

}
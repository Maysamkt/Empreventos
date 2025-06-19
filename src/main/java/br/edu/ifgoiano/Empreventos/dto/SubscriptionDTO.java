package br.edu.ifgoiano.Empreventos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class SubscriptionDTO {

    @NotNull(message = "O ID do evento é obrigatório.")
    private Long eventId;

    @NotNull(message = "O valor pago é obrigatório.")
    @PositiveOrZero(message = "O valor pago deve ser zero ou maior.")
    private BigDecimal amountPaid;


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}
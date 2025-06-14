package br.edu.ifgoiano.Empreventos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class SubscriptionDTO {

    @NotNull(message = "O ID do evento é obrigatório.")
    private Integer eventId;

    @NotNull(message = "O ID do participante é obrigatório.")
    private Integer listenerId;

    @NotNull(message = "O valor pago é obrigatório.")
    @PositiveOrZero(message = "O valor pago deve ser zero ou maior.")
    private BigDecimal amountPaid;


    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getListenerId() {
        return listenerId;
    }

    public void setListenerId(Integer listenerId) {
        this.listenerId = listenerId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}
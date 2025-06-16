package br.edu.ifgoiano.Empreventos.mapper;

import br.edu.ifgoiano.Empreventos.dto.request.InvoiceRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.InvoiceResponseDTO;
import br.edu.ifgoiano.Empreventos.model.Invoice;
import br.edu.ifgoiano.Empreventos.model.UserPlan;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvoiceMapper {

    public InvoiceResponseDTO toResponseDTO(Invoice invoice) {
        if (invoice == null) {
            return null;
        }

        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setUserPlanId(invoice.getUserPlan() != null ? invoice.getUserPlan().getId() : null);
        dto.setIssueDate(invoice.getIssueDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setAmount(invoice.getAmount());

        // Usa getDescricao() que retorna o nome em português
        if (invoice.getStatus() != null) {
            dto.setStatus(invoice.getStatus().getDescricao()); // Retorna "PENDENTE", "PAGO", etc.
        }

        if (invoice.getPaymentMethod() != null) {
            dto.setPaymentMethod(invoice.getPaymentMethod().getDescricao()); // Retorna "CARTAO_CREDITO", "PIX", etc.
        }

        dto.setPaymentDate(invoice.getPaymentDate());
        dto.setGatewayId(invoice.getGatewayId());
        dto.setBillingCycle(invoice.getBillingCycle());
        dto.setDiscount(invoice.getDiscount());
        dto.setCreated_at(invoice.getCreated_at());
        dto.setUpdated_at(invoice.getUpdated_at());

        return dto;
    }

    public Invoice toEntity(InvoiceRequestDTO dto, UserPlan userPlan) {
        if (dto == null) {
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setUserPlan(userPlan);
        invoice.setIssueDate(dto.getIssueDate());
        invoice.setDueDate(dto.getDueDate());
        invoice.setAmount(dto.getAmount());

        // Converte do português para o enum
        if (dto.getStatus() != null) {
            try {
                // Tenta converter direto do nome do enum em português
                invoice.setStatus(Invoice.InvoiceStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                // Se não conseguir, tenta converter do valor do banco
                invoice.setStatus(Invoice.InvoiceStatus.fromValorBanco(dto.getStatus()));
            }
        } else {
            invoice.setStatus(Invoice.InvoiceStatus.PENDENTE);
        }

        if (dto.getPaymentMethod() != null) {
            try {
                invoice.setPaymentMethod(Invoice.PaymentMethod.valueOf(dto.getPaymentMethod()));
            } catch (IllegalArgumentException e) {
                invoice.setPaymentMethod(Invoice.PaymentMethod.fromValorBanco(dto.getPaymentMethod()));
            }
        }

        invoice.setPaymentDate(dto.getPaymentDate());
        invoice.setGatewayId(dto.getGatewayId());
        invoice.setBillingCycle(dto.getBillingCycle());
        invoice.setDiscount(dto.getDiscount() != null ? dto.getDiscount() : BigDecimal.ZERO);

        return invoice;
    }

    public void updateEntityFromDTO(InvoiceRequestDTO dto, Invoice entity, UserPlan userPlan) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setUserPlan(userPlan);
        entity.setIssueDate(dto.getIssueDate());
        entity.setDueDate(dto.getDueDate());
        entity.setAmount(dto.getAmount());

        if (dto.getStatus() != null) {
            try {
                entity.setStatus(Invoice.InvoiceStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                entity.setStatus(Invoice.InvoiceStatus.fromValorBanco(dto.getStatus()));
            }
        }

        if (dto.getPaymentMethod() != null) {
            try {
                entity.setPaymentMethod(Invoice.PaymentMethod.valueOf(dto.getPaymentMethod()));
            } catch (IllegalArgumentException e) {
                entity.setPaymentMethod(Invoice.PaymentMethod.fromValorBanco(dto.getPaymentMethod()));
            }
        }

        entity.setPaymentDate(dto.getPaymentDate());
        entity.setGatewayId(dto.getGatewayId());
        entity.setBillingCycle(dto.getBillingCycle());
        entity.setDiscount(dto.getDiscount() != null ? dto.getDiscount() : BigDecimal.ZERO);
    }
}
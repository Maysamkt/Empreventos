package br.edu.ifgoiano.Empreventos.service;


import br.edu.ifgoiano.Empreventos.dto.request.InvoiceRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.InvoiceResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.InvoiceMapper;
import br.edu.ifgoiano.Empreventos.model.Invoice;
import br.edu.ifgoiano.Empreventos.model.UserPlan;
import br.edu.ifgoiano.Empreventos.repository.InvoiceRepository;
import br.edu.ifgoiano.Empreventos.repository.UserPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserPlanRepository userPlanRepository;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          UserPlanRepository userPlanRepository,
                          InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.userPlanRepository = userPlanRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public List<InvoiceResponseDTO> findAll() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InvoiceResponseDTO findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fatura com ID " + id + " não encontrada"));
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Transactional
    public InvoiceResponseDTO create(InvoiceRequestDTO dto) {
        validateDates(dto.getIssueDate(), dto.getDueDate());

        UserPlan userPlan = userPlanRepository.findById(dto.getUserPlanId())
                .orElseThrow(() -> new NoSuchElementException("Plano de usuário com ID " + dto.getUserPlanId() + " não encontrado"));

        Invoice invoice = invoiceMapper.toEntity(dto, userPlan);
        Invoice savedInvoice = invoiceRepository.save(invoice);

        return invoiceMapper.toResponseDTO(savedInvoice);
    }

    @Transactional
    public InvoiceResponseDTO update(Long id, InvoiceRequestDTO dto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fatura com ID " + id + " não encontrada"));

        validateDates(dto.getIssueDate(), dto.getDueDate());

        UserPlan userPlan = userPlanRepository.findById(dto.getUserPlanId())
                .orElseThrow(() -> new NoSuchElementException("Plano de usuário com ID " + dto.getUserPlanId() + " não encontrado"));

        invoiceMapper.updateEntityFromDTO(dto, existingInvoice, userPlan);
        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);

        return invoiceMapper.toResponseDTO(updatedInvoice);
    }

    @Transactional
    public void delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fatura com ID " + id + " não encontrada"));

        invoice.setDeleted_at(new java.util.Date());
        invoiceRepository.save(invoice);
    }

    public List<InvoiceResponseDTO> findByStatus(String status) {
        // Converte do português para o enum
        Invoice.InvoiceStatus enumStatus = Invoice.InvoiceStatus.valueOf(status);
        return invoiceRepository.findByStatus(enumStatus).stream()
                .map(invoiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public InvoiceResponseDTO markAsPaid(Long id, String paymentMethod, LocalDate paymentDate, String gatewayId) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fatura com ID " + id + " não encontrada"));

        invoice.setStatus(Invoice.InvoiceStatus.PAGO);
        invoice.setPaymentMethod(Invoice.PaymentMethod.valueOf(paymentMethod));
        invoice.setPaymentDate(paymentDate);
        invoice.setGatewayId(gatewayId);

        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(updatedInvoice);
    }

    @Transactional
    public void updateOverdueInvoices() {
        List<Invoice> overdueInvoices = invoiceRepository
                .findByStatusAndDueDateBefore(Invoice.InvoiceStatus.PENDENTE, LocalDate.now());

        overdueInvoices.forEach(invoice -> {
            invoice.setStatus(Invoice.InvoiceStatus.ATRASADO);
            invoiceRepository.save(invoice);
        });
    }


    private void validateDates(LocalDate issueDate, LocalDate dueDate) {
        if (dueDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("Data de vencimento não pode ser anterior à data de emissão");
        }
    }
}
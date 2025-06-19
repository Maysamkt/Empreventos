package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.InvoiceRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.InvoiceResponseDTO;
import br.edu.ifgoiano.Empreventos.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/faturas")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        List<InvoiceResponseDTO> invoices = invoiceService.findAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceResponseDTO invoice = invoiceService.findById(id);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody InvoiceRequestDTO dto) {
        // Valores aceitos para status:
        // "PENDENTE" ou "PENDING"
        // "PAGO" ou "PAID"
        // "ATRASADO" ou "OVERDUE"
        // "CANCELADO" ou "CANCELED"

        // Valores aceitos para paymentMethod:
        // "CARTAO_CREDITO" ou "CREDIT_CARD"
        // "PIX" (apenas)
        // "BOLETO" ou "BANK_SLIP"
        // "TRANSFERENCIA" ou "TRANSFER"
        InvoiceResponseDTO createdInvoice = invoiceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequestDTO dto) {
        InvoiceResponseDTO updatedInvoice = invoiceService.update(id, dto);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByStatus(
            @PathVariable String status) {
        List<InvoiceResponseDTO> invoices = invoiceService.findByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<InvoiceResponseDTO> markInvoiceAsPaid(
            @PathVariable Long id,
            @RequestParam String paymentMethod,
            @RequestParam String paymentDate,
            @RequestParam(required = false) String gatewayId) {

        LocalDate date = LocalDate.parse(paymentDate);
        InvoiceResponseDTO paidInvoice = invoiceService.markAsPaid(id, paymentMethod, date, gatewayId);
        return ResponseEntity.ok(paidInvoice);
    }
}

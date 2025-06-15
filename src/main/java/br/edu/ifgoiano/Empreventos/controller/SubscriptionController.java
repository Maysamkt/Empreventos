package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.SubscriptionDTO;
import br.edu.ifgoiano.Empreventos.dto.SubscriptionResponseDTO;
import br.edu.ifgoiano.Empreventos.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> create(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        var createdSubscription = subscriptionService.create(subscriptionDTO);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> findById(@PathVariable Integer id) {
        var subscription = subscriptionService.findById(id);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<SubscriptionResponseDTO> cancel(@PathVariable Integer id) {
        var cancelledSubscription = subscriptionService.cancel(id);
        return ResponseEntity.ok(cancelledSubscription);
    }
}
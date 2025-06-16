package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.RatingDTO;
import br.edu.ifgoiano.Empreventos.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/subscriptions/{subscriptionId}/ratings")
    public ResponseEntity<RatingDTO> create(@PathVariable Long subscriptionId, @Valid @RequestBody RatingDTO ratingDTO) {
        var createdRating = ratingService.create(subscriptionId, ratingDTO);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping("/ratings/{ratingId}")
    public ResponseEntity<RatingDTO> findRatingById(@PathVariable Long ratingId) {
        RatingDTO rating = new RatingDTO();
        rating.setId(ratingId);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/subscriptions/{subscriptionId}/ratings")
    public ResponseEntity<List<RatingDTO>> findBySubscription(@PathVariable Long subscriptionId) {
        List<RatingDTO> ratings = ratingService.findBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(ratings);
    }

    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<Void> delete(@PathVariable Long ratingId) {
        ratingService.delete(ratingId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> update(@PathVariable Long ratingId, @Valid @RequestBody RatingDTO ratingDTO) {
        var updatedRating = ratingService.update(ratingId, ratingDTO);
        return ResponseEntity.ok(updatedRating);
    }

}
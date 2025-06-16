package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.RatingDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Rating;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import br.edu.ifgoiano.Empreventos.repository.RatingRepository;
import br.edu.ifgoiano.Empreventos.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

   @Autowired
    private SubscriptionRepository subscriptionRepository;

    public RatingDTO create(Long subscriptionId, RatingDTO ratingDTO) {
        var subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada com ID: " + subscriptionId));
        var rating = DataMapper.parseObject(ratingDTO, Rating.class);
        rating.setSubscription(subscription);
        var savedRating = ratingRepository.save(rating);
        return DataMapper.parseObject(savedRating, RatingDTO.class);
    }

    public List<RatingDTO> findBySubscriptionId(Long subscriptionId) {
        var ratings = ratingRepository.findBySubscriptionId(subscriptionId);
        return DataMapper.parseListObjects(ratings, RatingDTO.class);
    }

    public Optional<Rating> findRatingByUserAndEvent(Long userId, Long eventId) {
        return ratingRepository.findByUserAndEvent(userId, eventId);
    }

    public List<RatingDTO> findByEventId(Long eventId) {
        var ratings = ratingRepository.findByEventId(eventId);
        return DataMapper.parseListObjects(ratings, RatingDTO.class);
    }

    public void delete(Long ratingId) {
        var rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação com ID " + ratingId + " não encontrada."));
        rating.setDeletedAt(LocalDateTime.now());
        ratingRepository.save(rating);
    }

    public RatingDTO update(Long ratingId, RatingDTO ratingDTO) {
        var rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação com ID " + ratingId + " não encontrada."));
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());

        var updatedRating = ratingRepository.save(rating);
        return DataMapper.parseObject(updatedRating, RatingDTO.class);
    }
}
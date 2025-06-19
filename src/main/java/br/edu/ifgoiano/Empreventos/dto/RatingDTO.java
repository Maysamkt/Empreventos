package br.edu.ifgoiano.Empreventos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RatingDTO {

    private Long id;

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private int score;

    @Size(max = 500, message = "O comentário não pode exceder 500 caracteres.")
    private String comment;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
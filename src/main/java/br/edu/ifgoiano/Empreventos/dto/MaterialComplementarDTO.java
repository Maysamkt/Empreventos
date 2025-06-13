package br.edu.ifgoiano.Empreventos.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class MaterialComplementarDTO {

    private Long id;

    @NotBlank(message = "O título do material é obrigatório.")
    private String titulo;

    private String descricao;

    @NotBlank(message = "A URL do arquivo é obrigatória.")
    @URL(message = "A URL do arquivo deve ser válida.")
    private String urlArquivo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }
}
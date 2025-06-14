package br.edu.ifgoiano.Empreventos.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityResponseDTO {
    private Long id;
    private String titulo;
    private String tipoAtividade;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private List<MaterialComplementarDTO> materiais;


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

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public List<MaterialComplementarDTO> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<MaterialComplementarDTO> materiais) {
        this.materiais = materiais;
    }
}
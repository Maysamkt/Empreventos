package br.edu.ifgoiano.Empreventos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String local;
    private int capacidade;
    private String status;
    private BigDecimal valorInscricao;
    private boolean eventoPago;
   // private Long empresaOrganizadoraId;

    public EventDTO() {

    }

    public EventDTO(String titulo, String descricao, LocalDate dataInicio, LocalDate dataFim,
                    String local, int capacidade, String status, BigDecimal valorInscricao,
                    boolean eventoPago) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.capacidade = capacidade;
        this.status = status;
        this.valorInscricao = valorInscricao;
        this.eventoPago = eventoPago;
    }

    // Getters e Setters
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorInscricao() {
        return valorInscricao;
    }

    public void setValorInscricao(BigDecimal valorInscricao) {
        this.valorInscricao = valorInscricao;
    }

    public boolean isEventoPago() {
        return eventoPago;
    }

    public void setEventoPago(boolean eventoPago) {
        this.eventoPago = eventoPago;
    }

  /*  public Long getEmpresaOrganizadoraId() {
        return empresaOrganizadoraId;
    }

    public void setEmpresaOrganizadoraId(Long empresaOrganizadoraId) {
        this.empresaOrganizadoraId = empresaOrganizadoraId;
    }*/
}
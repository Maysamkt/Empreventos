package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "eventos")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "local", nullable = false)
    private String local;

    @Column(name = "capacidade", nullable = false)
    private int capacidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEvento status;

    @Column(name = "valor_inscricao")
    private BigDecimal valorInscricao;

    @Column(name = "pago")
    private boolean eventoPago;

    /* Relacionamentos
    @ManyToOne
    @JoinColumn(name = "empresa_organizadora_id")
    private EmpresaOrganizadora empresaOrganizadora;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Atividade> atividades;

    @OneToMany(mappedBy = "evento")
    private List<Inscricao> inscricoes;

    @OneToMany(mappedBy = "evento")
    private List<Avaliacao> avaliacoes;
*/

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

    public StatusEvento getStatus() {
        return status;
    }

    public void setStatus(StatusEvento status) {
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
}

package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Evento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private String Local;
    private int capacidade;
    private String statusEvento;
    private BigDecimal valorInscricao;
    private boolean pago;

}

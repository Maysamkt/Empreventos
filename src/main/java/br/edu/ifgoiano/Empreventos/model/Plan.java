package br.edu.ifgoiano.Empreventos.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "plan")
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;
}

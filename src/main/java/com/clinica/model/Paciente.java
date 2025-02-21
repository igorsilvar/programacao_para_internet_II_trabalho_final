/**
 * Classe que representa um Paciente no sistema
 * Contém todas as informações pessoais e médicas do paciente
 */
package com.clinica.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa um paciente no sistema
 */
@Entity
@Table(name = "pacientes")
@Data
public class Paciente {
    
    /** ID único do paciente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome completo do paciente */
    @Column(nullable = false)
    private String nome;
    
    /** Número do SUS do paciente */
    @Column(name = "numero_sus", unique = true)
    private String numeroSus;
    
    /** Telefone para contato */
    private String telefone;
    
    /** Rua do paciente */
    private String rua;
    
    /** Bairro do paciente */
    private String bairro;
}

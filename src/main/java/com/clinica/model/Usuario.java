package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username é obrigatório")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role = "ADMIN";
}

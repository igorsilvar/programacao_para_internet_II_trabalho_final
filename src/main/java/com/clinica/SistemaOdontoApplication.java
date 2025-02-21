/**
 * Classe principal da aplicação do Sistema Odontológico
 * Responsável por iniciar a aplicação Spring Boot
 */
package com.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaOdontoApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(SistemaOdontoApplication.class, args);
    }
}

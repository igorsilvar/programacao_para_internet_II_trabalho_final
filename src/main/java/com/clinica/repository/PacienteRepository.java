/**
 * Interface de repositório para a entidade Paciente
 * Fornece métodos para acessar e manipular dados dos pacientes no banco de dados
 */
package com.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clinica.model.Paciente;
import java.util.Optional;

/**
 * Repository para operações de CRUD relacionadas aos Pacientes
 * Estende JpaRepository para herdar operações básicas de banco de dados
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    /**
     * Busca um paciente pelo número do SUS
     * @param numeroSus número do SUS do paciente
     * @return Optional contendo o paciente se encontrado
     */
    Optional<Paciente> findByNumeroSus(String numeroSus);
    
    /**
     * Verifica se existe um paciente com o número do SUS informado
     * @param numeroSus número do SUS a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByNumeroSus(String numeroSus);
}

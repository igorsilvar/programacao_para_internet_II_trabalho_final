/**
 * Interface de repositório para a entidade Usuario
 * Fornece métodos para acessar e manipular dados dos usuários no banco de dados
 */
package com.clinica.repository;

import com.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository para operações de CRUD relacionadas aos Usuários
 * Estende JpaRepository para herdar operações básicas de banco de dados
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca um usuário pelo seu nome de usuário (login)
     * @param username nome de usuário a ser buscado
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByUsername(String username);
    
    /**
     * Verifica se existe um usuário com o username informado
     * @param username nome de usuário a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByUsername(String username);
}

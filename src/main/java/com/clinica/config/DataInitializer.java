/**
 * Classe responsável por inicializar dados básicos no sistema
 * Cria usuário admin padrão caso não exista
 */
package com.clinica.config;

import com.clinica.model.Usuario;
import com.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Componente que inicializa dados básicos no sistema
 * Executado automaticamente na inicialização da aplicação
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    /** Repositório de usuários */
    private final UsuarioRepository usuarioRepository;
    
    /** Codificador de senha */
    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor do inicializador de dados
     * @param usuarioRepository repositório para acesso aos dados dos usuários
     * @param passwordEncoder codificador para criptografia de senhas
     */
    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método executado na inicialização da aplicação
     * Cria usuário admin caso não exista
     * @param args argumentos de linha de comando
     */
    @Override
    public void run(String... args) {
        // Verifica se já existe um usuário admin
        Optional<Usuario> adminExistente = usuarioRepository.findByUsername("admin@admin.com");
        
        if (adminExistente.isEmpty()) {
            // Cria usuário admin padrão
            Usuario admin = new Usuario();
            admin.setUsername("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            
            usuarioRepository.save(admin);
            
            logger.info("Usuário admin criado com sucesso!");
            logger.info("Username: admin@admin.com");
            logger.info("Senha: admin123");
            logger.info("Senha encriptada: {}", passwordEncoder.encode("admin123"));
        }
    }
}

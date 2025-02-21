/**
 * Controlador responsável pela autenticação de usuários
 * Gerencia operações de login, logout e registro de usuários
 */
package com.clinica.controller;

import com.clinica.model.Usuario;
import com.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

/**
 * Controlador de autenticação de usuários
 */
@Controller
public class AuthController {

    /** Repositório de usuários para acesso aos dados */
    private final UsuarioRepository usuarioRepository;
    
    /** Codificador de senha para criptografia */
    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor do controlador de autenticação
     * @param usuarioRepository repositório para acesso aos dados dos usuários
     * @param passwordEncoder codificador de senha para criptografia
     */
    @Autowired
    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Exibe a página de login
     * @return nome da view de login
     */
    @GetMapping("/login")
    public String login() {
        // Se já estiver autenticado, redireciona para o dashboard
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    /**
     * Exibe o dashboard do usuário
     * @param model modelo para binding de dados
     * @return nome da view do dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("role", usuario.getRole());
        return "dashboard";
    }

    /**
     * Exibe o formulário de registro de novo usuário
     * @param model modelo para binding de dados
     * @return nome da view do formulário de registro
     */
    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    /**
     * Processa o registro de um novo usuário
     * @param usuario dados do usuário a ser registrado
     * @param result resultado da validação do formulário
     * @param redirectAttributes atributos para redirecionamento
     * @return redirecionamento após o registro
     */
    @PostMapping("/cadastro")
    public String cadastrarUsuario(@Valid @ModelAttribute Usuario usuario,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastro";
        }

        // Verifica se o username já existe
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            result.rejectValue("username", "error.usuario", "Username já está em uso");
            return "cadastro";
        }

        try {
            // Criptografa a senha antes de salvar
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setRole("USER");
            usuarioRepository.save(usuario);
            
            redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso!");
            return "redirect:/login";
        } catch (Exception e) {
            result.reject("error.usuario", "Erro ao salvar usuário: " + e.getMessage());
            return "cadastro";
        }
    }
}

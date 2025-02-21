/**
 * Controlador responsável pela página inicial e rotas básicas do sistema
 * Gerencia redirecionamentos e páginas estáticas
 */
package com.clinica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para gerenciar a página inicial e rotas básicas
 */
@Controller
public class HomeController {

    /**
     * Redireciona a raiz do site para a página inicial
     * @return redirecionamento para a página inicial
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    /**
     * Exibe a página inicial do sistema
     * @return nome da view da página inicial
     */
    @GetMapping("/home")
    public String index() {
        return "home";
    }
}

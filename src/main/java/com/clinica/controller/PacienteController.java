/**
 * Controlador responsável por gerenciar as operações relacionadas aos Pacientes
 * Fornece endpoints para CRUD de pacientes e gerenciamento de suas informações
 */
package com.clinica.controller;

import com.clinica.model.Paciente;
import com.clinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos Pacientes
 * Fornece endpoints para CRUD de pacientes e gerenciamento de suas informações
 */
@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    /** Repositório de pacientes para acesso aos dados */
    @Autowired
    private final PacienteRepository pacienteRepository;

    /**
     * Construtor do controlador de pacientes
     * @param pacienteRepository repositório para acesso aos dados dos pacientes
     */
    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Exibe a página de listagem de pacientes
     * @param model Model do Spring para passar dados à view
     * @return nome da view de listagem de pacientes
     */
    @GetMapping
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteRepository.findAll());
        return "pacientes/lista";
    }

    /**
     * Exibe o formulário para cadastro de novo paciente
     * @param model Model do Spring para passar dados à view
     * @return nome da view do formulário de cadastro
     */
    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/form";
    }

    /**
     * Processa o formulário de cadastro/edição de paciente
     * @param paciente dados do paciente a ser salvo
     * @param redirectAttributes resultado da validação do formulário
     * @return redirecionamento após o salvamento
     */
    @PostMapping("/salvar")
    public String salvarPaciente(@ModelAttribute Paciente paciente, RedirectAttributes redirectAttributes) {
        try {
            pacienteRepository.save(paciente);
            redirectAttributes.addFlashAttribute("mensagem", "Paciente salvo com sucesso!");
            return "redirect:/pacientes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar paciente: " + e.getMessage());
            return "redirect:/pacientes/novo";
        }
    }

    /**
     * Exibe o formulário de edição de paciente
     * @param id ID do paciente a ser editado
     * @param model Model do Spring para passar dados à view
     * @return nome da view do formulário de edição
     */
    @GetMapping("/{id}")
    public String editarPaciente(@PathVariable Long id, Model model) {
        model.addAttribute("paciente", pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de paciente inválido: " + id)));
        return "pacientes/form";
    }

    /**
     * Remove um paciente do sistema
     * @param id ID do paciente a ser removido
     * @param redirectAttributes resultado da remoção
     * @return redirecionamento após a remoção
     */
    @PostMapping("/{id}/excluir")
    public String excluirPaciente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pacienteRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Paciente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir paciente: " + e.getMessage());
        }
        return "redirect:/pacientes";
    }
}

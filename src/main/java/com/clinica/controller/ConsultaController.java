/**
 * Controlador responsável por gerenciar as operações relacionadas às Consultas
 * Fornece endpoints para agendamento, cancelamento e gerenciamento de consultas
 */
package com.clinica.controller;

import com.clinica.model.Consulta;
import com.clinica.model.Paciente;
import com.clinica.model.StatusConsulta;
import com.clinica.repository.ConsultaRepository;
import com.clinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas às Consultas
 * Fornece endpoints para agendamento, cancelamento e gerenciamento de consultas
 */
@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    /**
     * Repositório de consultas para acesso aos dados das consultas
     */
    @Autowired
    private ConsultaRepository consultaRepository;

    /**
     * Repositório de pacientes para acesso aos dados dos pacientes
     */
    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Exibe a página de listagem de consultas
     * @param model Model do Spring para passar dados à view
     * @return nome da view de listagem de consultas
     */
    @GetMapping
    public String listarConsultas(Model model) {
        LocalDateTime hoje = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        List<Consulta> proximasConsultas = consultaRepository.findByDataHoraGreaterThanEqualAndStatus(
            hoje, StatusConsulta.AGENDADA);
        model.addAttribute("consultas", proximasConsultas);
        return "consultas/lista";
    }

    /**
     * Exibe o formulário para agendamento de nova consulta
     * @param model Model do Spring para passar dados à view
     * @return nome da view do formulário de agendamento
     */
    @GetMapping("/agendar")
    public String mostrarFormAgendamento(Model model) {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("pacientes", pacienteRepository.findAll());
        model.addAttribute("statusConsulta", StatusConsulta.values());
        return "consultas/agendar";
    }

    /**
     * Processa o agendamento de uma nova consulta
     * @param pacienteId ID do paciente da consulta
     * @param data data da consulta
     * @param hora hora da consulta
     * @param observacoes observações da consulta
     * @param redirectAttributes resultado da operação
     * @return redirecionamento após o agendamento
     */
    @PostMapping("/agendar")
    public String agendarConsulta(
            @RequestParam Long pacienteId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime hora,
            @RequestParam(required = false) String observacoes,
            RedirectAttributes redirectAttributes) {
        try {
            Paciente paciente = pacienteRepository.findById(pacienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

            Consulta consulta = new Consulta();
            consulta.setPaciente(paciente);
            consulta.setDataHora(LocalDateTime.of(data, hora));
            consulta.setObservacoes(observacoes);
            consulta.setStatus(StatusConsulta.AGENDADA);

            consultaRepository.save(consulta);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta agendada com sucesso!");
            return "redirect:/consultas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao agendar consulta: " + e.getMessage());
            return "redirect:/consultas/agendar";
        }
    }

    /**
     * Exibe a página de edição de uma consulta
     * @param id ID da consulta a ser editada
     * @param model Model do Spring para passar dados à view
     * @return nome da view de edição da consulta
     */
    @GetMapping("/{id}")
    public String editarConsulta(@PathVariable Long id, Model model) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de consulta inválido: " + id));
        
        model.addAttribute("consulta", consulta);
        model.addAttribute("pacientes", pacienteRepository.findAll());
        model.addAttribute("statusConsulta", StatusConsulta.values());
        return "consultas/agendar";
    }

    /**
     * Cancela uma consulta agendada
     * @param id ID da consulta a ser cancelada
     * @param redirectAttributes resultado da operação
     * @return redirecionamento após o cancelamento
     */
    @PostMapping("/{id}/cancelar")
    public String cancelarConsulta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Consulta consulta = consultaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
            
            consulta.setStatus(StatusConsulta.CANCELADA);
            consultaRepository.save(consulta);
            
            redirectAttributes.addFlashAttribute("mensagem", "Consulta cancelada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar consulta: " + e.getMessage());
        }
        return "redirect:/consultas";
    }

    /**
     * Confirma uma consulta agendada
     * @param id ID da consulta a ser confirmada
     * @param redirectAttributes resultado da operação
     * @return redirecionamento após a confirmação
     */
    @PostMapping("/{id}/confirmar")
    public String confirmarConsulta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Consulta consulta = consultaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
            
            consulta.setStatus(StatusConsulta.CONFIRMADA);
            consultaRepository.save(consulta);
            
            redirectAttributes.addFlashAttribute("mensagem", "Consulta confirmada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao confirmar consulta: " + e.getMessage());
        }
        return "redirect:/consultas";
    }
}

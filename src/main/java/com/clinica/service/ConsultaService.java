package com.clinica.service;

import com.clinica.model.Consulta;
import com.clinica.model.Paciente;
import com.clinica.model.StatusConsulta;
import com.clinica.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public List<Consulta> listarConsultasPaciente(Paciente paciente) {
        return consultaRepository.findByPaciente(paciente);
    }

    public List<Consulta> listarConsultasPorStatus(StatusConsulta status) {
        return consultaRepository.findByStatus(status);
    }

    public List<Consulta> listarConsultasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return consultaRepository.findByDataHoraBetween(inicio, fim);
    }

    public Consulta agendarConsulta(Consulta consulta) {
        // Validações básicas
        if (consulta.getPaciente() == null) {
            throw new IllegalArgumentException("Paciente é obrigatório");
        }
        if (consulta.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora são obrigatórios");
        }
        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data da consulta não pode ser no passado");
        }

        // Verifica se já existe consulta no mesmo horário
        List<Consulta> consultasNoHorario = consultaRepository.findByDataHoraBetween(
            consulta.getDataHora(), 
            consulta.getDataHora().plusMinutes(30)
        );
        
        if (!consultasNoHorario.isEmpty()) {
            throw new IllegalArgumentException("Já existe uma consulta agendada para este horário");
        }

        // Define o status inicial
        consulta.setStatus(StatusConsulta.AGENDADA);
        
        return consultaRepository.save(consulta);
    }

    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new IllegalArgumentException("Não é possível cancelar uma consulta já realizada");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    public void confirmarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new IllegalArgumentException("Não é possível confirmar uma consulta cancelada");
        }

        consulta.setStatus(StatusConsulta.CONFIRMADA);
        consultaRepository.save(consulta);
    }

    public void realizarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new IllegalArgumentException("Não é possível realizar uma consulta cancelada");
        }

        consulta.setStatus(StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);
    }
}

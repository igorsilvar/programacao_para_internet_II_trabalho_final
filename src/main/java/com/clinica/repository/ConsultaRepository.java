package com.clinica.repository;

import com.clinica.model.Consulta;
import com.clinica.model.Paciente;
import com.clinica.model.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPaciente(Paciente paciente);
    List<Consulta> findByStatus(StatusConsulta status);
    List<Consulta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByDataHoraGreaterThanEqualAndStatus(LocalDateTime dataHora, StatusConsulta status);
}

package org.example.turnos.repositorios;

import org.example.turnos.modelo.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepositorio extends JpaRepository<Servicio, Long> {

}
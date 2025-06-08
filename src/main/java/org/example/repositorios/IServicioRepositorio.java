package org.example.repositorios;

import org.example.modelo.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepositorio extends JpaRepository<Servicio, Long> {
}

package org.example.turnos.repositorios;

import java.util.Optional;
import org.example.turnos.modelo.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepositorio extends JpaRepository<Servicio, Long> {

	
	Optional<Servicio> findByDescripcion(String descripcion);


}
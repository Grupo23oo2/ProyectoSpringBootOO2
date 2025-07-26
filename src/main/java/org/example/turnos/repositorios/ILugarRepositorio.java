package org.example.turnos.repositorios;

import java.util.Optional;


import org.example.turnos.modelo.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILugarRepositorio extends JpaRepository<Lugar, Long> {
	
	boolean existsByDireccion(String direccion);
	
	Optional<Lugar> findByDireccion(String direccion);
}

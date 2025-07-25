package org.example.turnos.repositorios;

import java.util.Optional;

import org.example.turnos.modelo.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolUsuarioRepositorio extends JpaRepository<RolUsuario, Long> {
	
	Optional<RolUsuario> findByRole(String role);
}

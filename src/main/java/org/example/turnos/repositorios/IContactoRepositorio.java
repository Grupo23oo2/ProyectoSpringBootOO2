package org.example.turnos.repositorios;

import java.util.Optional;

import org.example.turnos.modelo.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactoRepositorio extends JpaRepository<Contacto, Long> {
	Optional<Contacto> findByEmail(String email);
	void deleteByEmail(String email);
	boolean existsByEmail(String email);
}

package org.example.turnos.repositorios;

import java.util.Optional;

import org.example.turnos.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}

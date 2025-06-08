package org.example.repositorios;

import org.example.modelo.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolUsuarioRepositorio extends JpaRepository<RolUsuario, Long> {
}

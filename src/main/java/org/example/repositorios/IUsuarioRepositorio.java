package org.example.repositorios;

import org.example.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {

}

package org.example.repositorios;

import org.example.modelo.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactoRepositorio extends JpaRepository<Contacto, Long> {
}

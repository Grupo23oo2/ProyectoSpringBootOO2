package org.example.turnos.repositorios;

import org.example.turnos.modelo.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactoRepositorio extends JpaRepository<Contacto, Long> {
}

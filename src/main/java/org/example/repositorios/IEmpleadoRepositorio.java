package org.example.repositorios;

import org.example.modelo.Empleado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoRepositorio extends JpaRepository<Empleado, Long> {
    // Podés agregar métodos personalizados si los necesitás
}

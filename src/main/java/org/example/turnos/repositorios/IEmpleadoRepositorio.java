package org.example.turnos.repositorios;

import org.example.turnos.modelo.Empleado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoRepositorio extends JpaRepository<Empleado, Long> {
	// Podés agregar métodos personalizados si los necesitás
	@Query("""
			    SELECT e FROM Empleado e
			    JOIN e.usuario u
			    JOIN u.rolesUsuario ru
			    WHERE ru.role = :role
			""")
	List<Empleado> findByRol(@Param("role") String role);

	List<Empleado> findByFechaInicio(LocalDate fechaInicio);
}

package org.example.turnos.repositorios;

import org.example.turnos.modelo.Empleado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoRepositorio extends JpaRepository<Empleado, Long> {

	@Query("""
    SELECT e FROM Empleado e
    WHERE e.usuario.role = :role
	""")
	List<Empleado> empleadosPorRol(@Param("role") String role);

	List<Empleado> findByFechaInicio(LocalDate fechaInicio);
	
	boolean existsByDni(String dni); 
}

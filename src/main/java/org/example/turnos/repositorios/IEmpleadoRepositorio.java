package org.example.turnos.repositorios;

import org.example.turnos.modelo.Empleado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpleadoRepositorio extends JpaRepository<Empleado, Long> {

	//antes
	/*@Query("""
			    SELECT e FROM Empleado e
			    JOIN e.usuario u
			    JOIN u.rolesUsuario ru
			    WHERE ru.role = :role
			""")
	List<Empleado> findByRol(@Param("role") String role);*/
	@Query("""
		    SELECT e FROM Empleado e
		    WHERE e.usuario.rol = :rol
		""")
	List<Empleado> findByRol(@Param("rol") String rol);

	List<Empleado> findByFechaInicio(LocalDate fechaInicio);
	
	boolean existsByDni(String dni); 
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Empleado e WHERE e.dni = :dni")
	void deleteByDni(@Param("dni") String dni);
}

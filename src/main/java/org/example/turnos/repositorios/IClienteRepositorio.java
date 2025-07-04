package org.example.turnos.repositorios;

import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Contacto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClienteRepositorio extends JpaRepository<Cliente, Long> {

	@Query("""
			    SELECT c FROM Cliente c
			    JOIN c.usuario u
			    JOIN u.rolesUsuario ru
			    WHERE ru.role = :role
			""")
	List<Cliente> findByRol(@Param("role") String role);

	List<Cliente> findByCuit(String cuit);
	
	boolean existsByCuit(String cuit); //para la excepcion de cuit duplicado
	boolean existsByDni(String dni); //para la excepcion de dni duplicado

	@Query("""
			    SELECT c.contacto FROM Cliente c
			    WHERE c.cuit = :cuit
			""")
	Contacto findContactoByCuit(@Param("cuit") String cuit);

}

package org.example.turnos.repositorios;


import java.util.List;

import org.example.turnos.modelo.RolUsuario;
import org.example.turnos.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {
	@Query("""
		    SELECT r FROM RolUsuario r
		    WHERE r.usuario.idUsuario = :idUsuario
		""")
		List<RolUsuario> findRolesUsuarioByUsuario(@Param("idUsuario") Long idUsuario);



}

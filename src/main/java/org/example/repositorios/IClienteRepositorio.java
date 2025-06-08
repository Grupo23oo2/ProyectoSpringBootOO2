package org.example.repositorios;

import org.example.modelo.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClienteRepositorio extends JpaRepository<Cliente, Long> {
    // Podés agregar métodos personalizados si los necesitás
	@Query("SELECT c FROM Cliente c WHERE c.usuario.rol.nombre = :rol")
    List<Cliente> findByRol(@Param("rol") String rol);

    List<Cliente> findByCuit(String cuit);
}

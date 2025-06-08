package org.example.repositorios;


import org.example.modelo.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILugarRepositorio extends JpaRepository<Lugar, Long> {
}

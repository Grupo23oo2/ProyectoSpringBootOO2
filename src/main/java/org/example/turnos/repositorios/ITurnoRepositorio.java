package org.example.turnos.repositorios;

import org.example.turnos.modelo.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ITurnoRepositorio extends JpaRepository<Turno, Long> {

    // 1. Turnos entre dos fechas
    @Query("SELECT t FROM Turno t WHERE t.fechaHoraInicio >= :inicio AND t.fechaHoraFin <= :fin")
    List<Turno> buscarTurnosEntreFechas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // 2. Turnos de un cliente entre fechas
    @Query("SELECT t FROM Turno t WHERE t.cliente.idPersona = :idCliente AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosDeClienteEntreFechas(@Param("idCliente") Long idCliente,
                                                       @Param("inicio") LocalDateTime inicio,
                                                       @Param("fin") LocalDateTime fin);

    // 3. Turnos de un lugar entre fechas
    @Query("SELECT t FROM Turno t WHERE t.lugarTurno.idLugar = :idLugar AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosPorLugarYFechas(@Param("idLugar") Long idLugar,
                                                  @Param("inicio") LocalDateTime inicio,
                                                  @Param("fin") LocalDateTime fin);

    // 4. Turnos de un empleado entre fechas
    @Query("SELECT t FROM Turno t WHERE t.empleado.idPersona = :idEmpleado AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosDeEmpleadoEntreFechas(@Param("idEmpleado") Long idEmpleado,
                                                        @Param("inicio") LocalDateTime inicio,
                                                        @Param("fin") LocalDateTime fin);
    
 // 5. Turnos por fecha y tipo de servicio presencial
    @Query("SELECT t FROM Turno t WHERE t.presencial = :presencial AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosPorPresencialYFechas(@Param("presencial") boolean presencial,
                                                       @Param("inicio") LocalDateTime inicio,
                                                       @Param("fin") LocalDateTime fin);

    // 6. Turnos por fecha y nombre del cliente
    @Query("SELECT t FROM Turno t WHERE t.cliente.nombre = :nombreCliente AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosPorNombreClienteYFechas(@Param("nombreCliente") String nombreCliente,
                                                           @Param("inicio") LocalDateTime inicio,
                                                           @Param("fin") LocalDateTime fin);

    // 7. Turnos por fecha y rol del empleado
    @Query("""
    	    SELECT t FROM Turno t
    	    JOIN t.empleado e
    	    JOIN e.usuario u
    	    JOIN u.rolesUsuario r
    	    WHERE r.role = :rolEmpleado
    	      AND t.fechaHoraInicio BETWEEN :inicio AND :fin
    	""")
    	List<Turno> buscarTurnosPorRolEmpleadoYFechas(@Param("rolEmpleado") String rolEmpleado,
    	                                                    @Param("inicio") LocalDateTime inicio,
    	                                                    @Param("fin") LocalDateTime fin);

    // 8. Turnos por fecha y dirección del lugar
    @Query("SELECT t FROM Turno t WHERE t.lugarTurno.direccion = :direccionLugar AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosPorDireccionLugarYFechas(@Param("direccionLugar") String direccionLugar,
                                                           @Param("inicio") LocalDateTime inicio,
                                                           @Param("fin") LocalDateTime fin);
    
    @Query("SELECT t FROM Turno t WHERE t.presencial = :presencial")
    	List<Turno> obtenerTurnosPresenciales(@Param("presencial") boolean presencial);
    
    @Query("""
    	    SELECT t FROM Turno t
    	    WHERE LOWER(t.empleado.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))
    	""")
    	List<Turno> findTurnosByApellidoEmpleado(@Param("apellido") String apellido);
    
}
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
    @Query("SELECT t FROM Turno t WHERE t.cliente.cuit = :cuit AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosDeClientePorCuitEntreFechas(@Param("cuit") String cuit,
                                                         @Param("inicio") LocalDateTime inicio,
                                                         @Param("fin") LocalDateTime fin);


    // 3. Turnos de un lugar entre fechas
    @Query("SELECT t FROM Turno t WHERE t.lugarTurno.direccion = :direccion AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosPorDireccionYFechas(@Param("direccion") String direccion,
                                                 @Param("inicio") LocalDateTime inicio,
                                                 @Param("fin") LocalDateTime fin);


    @Query("SELECT t FROM Turno t WHERE t.empleado.dni = :dniEmpleado AND t.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Turno> buscarTurnosDeEmpleadoPorDniEntreFechas(@Param("dniEmpleado") String dniEmpleado,
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
<<<<<<< HEAD
    	    JOIN u.rolesUsuario r
    	    WHERE r.role = :rolEmpleado
=======
    	    WHERE u.rol = :rolEmpleado
>>>>>>> 83e45e824b156e44f84c81972db58fc84f24b7be
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


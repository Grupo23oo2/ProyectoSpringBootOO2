package org.example.repositorios;

import org.example.modelo.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IServicioRepositorio extends JpaRepository<Servicio, Long> {

    // 1. Servicios entre dos fechas
    @Query("SELECT s FROM Servicio s WHERE s.fechaHoraInicio >= :inicio AND s.fechaHoraFin <= :fin")
    List<Servicio> buscarServiciosEntreFechas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // 2. Servicios de un cliente entre fechas
    @Query("SELECT s FROM Servicio s WHERE s.cliente.idPersona = :idCliente AND s.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Servicio> buscarServiciosDeClienteEntreFechas(@Param("idCliente") Long idCliente,
                                                       @Param("inicio") LocalDateTime inicio,
                                                       @Param("fin") LocalDateTime fin);

    // 3. Servicios de un lugar entre fechas
    @Query("SELECT s FROM Servicio s WHERE s.lugarServicio.idLugar = :idLugar AND s.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Servicio> buscarServiciosPorLugarYFechas(@Param("idLugar") Long idLugar,
                                                  @Param("inicio") LocalDateTime inicio,
                                                  @Param("fin") LocalDateTime fin);

    // 4. Servicios de un empleado entre fechas
    @Query("SELECT s FROM Servicio s WHERE s.empleado.idPersona = :idEmpleado AND s.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Servicio> buscarServiciosDeEmpleadoEntreFechas(@Param("idEmpleado") Long idEmpleado,
                                                        @Param("inicio") LocalDateTime inicio,
                                                        @Param("fin") LocalDateTime fin);
    
 // 5. Servicios por fecha y tipo de servicio presencial
    @Query("SELECT s FROM Servicio s WHERE s.presencial = :presencial AND s.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Servicio> buscarServiciosPorPresencialYFechas(@Param("presencial") boolean presencial,
                                                       @Param("inicio") LocalDateTime inicio,
                                                       @Param("fin") LocalDateTime fin);

    // 6. Servicios por fecha y nombre del cliente
    @Query("SELECT s FROM Servicio s WHERE s.cliente.nombre = :nombreCliente AND s.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Servicio> buscarServiciosPorNombreClienteYFechas(@Param("nombreCliente") String nombreCliente,
                                                           @Param("inicio") LocalDateTime inicio,
                                                           @Param("fin") LocalDateTime fin);

    // 7. Servicios por fecha y rol del empleado
    @Query("""
    	    SELECT s FROM Servicio s
    	    JOIN s.empleado e
    	    JOIN e.usuario u
    	    JOIN u.rolesUsuario r
    	    WHERE r.role = :rolEmpleado
    	      AND s.fechaHoraInicio BETWEEN :inicio AND :fin
    	""")
    	List<Servicio> buscarServiciosPorRolEmpleadoYFechas(@Param("rolEmpleado") String rolEmpleado,
    	                                                    @Param("inicio") LocalDateTime inicio,
    	                                                    @Param("fin") LocalDateTime fin);

    // 8. Servicios por fecha y dirección del lugar
    @Query("SELECT s FROM Servicio s WHERE s.lugarServicio.direccion = :direccionLugar AND s.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Servicio> buscarServiciosPorDireccionLugarYFechas(@Param("direccionLugar") String direccionLugar,
                                                           @Param("inicio") LocalDateTime inicio,
                                                           @Param("fin") LocalDateTime fin);
    
    @Query("SELECT s FROM Servicio s WHERE s.presencial = :presencial")
    	List<Servicio> obtenerServiciosPresenciales(@Param("presencial") boolean presencial);
    
    @Query("""
    	    SELECT s FROM Servicio s
    	    WHERE LOWER(s.empleado.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))
    	""")
    	List<Servicio> findServiciosByApellidoEmpleado(@Param("apellido") String apellido);
    

}
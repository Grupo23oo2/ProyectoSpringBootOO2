package org.example.turnos.servicios;

import java.util.List;

import org.example.turnos.dtos.ServicioDTO;

public interface IServicioServicio {
    ServicioDTO agregarServicio(ServicioDTO servicioDTO);
    ServicioDTO traerServicio(Long id); 
    void eliminarServicio(Long id);
    ServicioDTO modificarServicio(Long id, ServicioDTO servicioDTO);
    List<ServicioDTO> traerServicios();
}


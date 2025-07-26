package org.example.turnos.servicios;

import java.util.List;

import org.example.turnos.dtos.ServicioDTO;

public interface IServicioServicio {
    ServicioDTO agregarServicio(ServicioDTO servicioDTO);

    ServicioDTO traerServicioPorDescripcion(String descripcion);

    void eliminarServicio(String descripcion);
    ServicioDTO modificarServicio(String descripcion, ServicioDTO servicioDTO);
    List<ServicioDTO> traerServicios();
}


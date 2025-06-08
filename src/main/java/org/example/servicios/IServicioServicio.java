package org.example.servicios;

import org.example.dtos.ServicioDTO;

import java.util.List;

public interface IServicioServicio {
    ServicioDTO agregarServicio(ServicioDTO dto);
    ServicioDTO traerServicio(Long id);
    List<ServicioDTO> traerServicios();
    ServicioDTO modificarServicio(Long id, ServicioDTO dto);  // solo fechas modificables
    void eliminarServicio(Long id);
}

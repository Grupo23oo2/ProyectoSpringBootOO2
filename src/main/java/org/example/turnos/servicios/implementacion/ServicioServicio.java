package org.example.turnos.servicios.implementacion;

import java.util.List;
import java.util.stream.Collectors;
import org.example.turnos.dtos.ServicioDTO;
import org.example.turnos.modelo.Servicio;
import org.example.turnos.repositorios.IServicioRepositorio;
import org.example.turnos.servicios.IServicioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioServicio implements IServicioServicio {

    @Autowired
    private IServicioRepositorio servicioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServicioDTO agregarServicio(ServicioDTO servicioDTO) {
        Servicio servicio = modelMapper.map(servicioDTO, Servicio.class);
        
        servicio.setActivo(true);
        
        Servicio guardado = servicioRepositorio.save(servicio);
        return modelMapper.map(guardado, ServicioDTO.class);
    }

    @Override

    public ServicioDTO traerServicioPorDescripcion(String descripcion) {
        Servicio servicio = servicioRepositorio.findByDescripcion(descripcion)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con descripción: " + descripcion));

        return modelMapper.map(servicio, ServicioDTO.class);
    }

    @Override
    public void eliminarServicio(String descripcion) {
        Servicio servicio = servicioRepositorio.findByDescripcion(descripcion)
            .orElseThrow(() -> new RuntimeException("No existe el servicio con descripción: " + descripcion));
        
        servicioRepositorio.delete(servicio);
    }

    @Override
    public ServicioDTO modificarServicio(String descripcion, ServicioDTO servicioDTO) {
        Servicio existente = servicioRepositorio.findByDescripcion(descripcion)
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado con descripción: " + descripcion));
        
        // Actualizar campos
        existente.setDescripcion(servicioDTO.getDescripcion());
        existente.setDuracion(servicioDTO.getDuracion());
        existente.setActivo(servicioDTO.isActivo());

        Servicio actualizado = servicioRepositorio.save(existente);
        return modelMapper.map(actualizado, ServicioDTO.class);
    }

    @Override
    public List<ServicioDTO> traerServicios() {
        List<Servicio> lista = servicioRepositorio.findAll();
        return lista.stream()
            .map(servicio -> modelMapper.map(servicio, ServicioDTO.class))
            .collect(Collectors.toList());
    }
}


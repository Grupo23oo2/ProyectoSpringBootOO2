package org.example.turnos.servicios.implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.example.turnos.dtos.ServicioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.excepciones.ServicioRelacionadoException;
import org.example.turnos.modelo.Servicio;
import org.example.turnos.repositorios.IServicioRepositorio;
import org.example.turnos.servicios.IServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServicio implements IServicioServicio {

    @Autowired
    private IServicioRepositorio servicioRepositorio;

    @Override
    public ServicioDTO agregarServicio(ServicioDTO servicioDTO) {
        try {
            Servicio servicio = toEntity(servicioDTO);
            servicio.setActivo(true); //por defecto es activo al crear el servicio

            Servicio guardado = servicioRepositorio.save(servicio);
            return toDTO(guardado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el servicio: " + e.getMessage());
        }
    }

    @Override
    public ServicioDTO traerServicioPorDescripcion(String descripcion) {
        try {
            Servicio servicio = servicioRepositorio.findByDescripcion(descripcion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con descripción: " + descripcion));
            return toDTO(servicio);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el servicio: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void eliminarServicio(String descripcion) {
        Servicio servicio = servicioRepositorio.findByDescripcion(descripcion)
                .orElseThrow(() -> new MiExcepcionPersonalizada("No existe el servicio con descripción: " + descripcion));
        try {
            servicioRepositorio.delete(servicio);
            servicioRepositorio.flush(); // <- esto fuerza la ejecución inmediata de SQL
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new ServicioRelacionadoException(
                "No se puede eliminar el servicio '" + descripcion + "' porque está asociado a turnos existentes."
            );
        }
    }

    @Override
    public ServicioDTO modificarServicio(String descripcion, ServicioDTO servicioDTO) {
        try {
            Servicio existente = servicioRepositorio.findByDescripcion(descripcion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con descripción: " + descripcion));

            //Actualizamos campos
            existente.setDescripcion(servicioDTO.descripcion());
            existente.setDuracion(servicioDTO.duracion());
            existente.setActivo(servicioDTO.activo());

            Servicio actualizado = servicioRepositorio.save(existente);
            return toDTO(actualizado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo modificar el servicio: " + e.getMessage());
        }
    }

    @Override
    public List<ServicioDTO> traerServicios() {
        try {
            return servicioRepositorio.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los servicios: " + e.getMessage());
        }
    }

    //metodos para mapeo manual
    private ServicioDTO toDTO(Servicio servicio) {
        return new ServicioDTO(
            servicio.getIdServicio(),
            servicio.getDescripcion(),
            servicio.getDuracion(),
            servicio.isActivo()
        );
    }

    private Servicio toEntity(ServicioDTO dto) {
        Servicio s = new Servicio();
        s.setIdServicio(dto.idServicio());
        s.setDescripcion(dto.descripcion());
        s.setDuracion(dto.duracion());
        s.setActivo(dto.activo());
        return s;
    }
}
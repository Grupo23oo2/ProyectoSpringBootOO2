package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.LugarCrearDTO;
import org.example.turnos.dtos.LugarDTO;
import org.example.turnos.excepciones.DireccionLugarDuplicadaException;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Lugar;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.turnos.repositorios.ILugarRepositorio;
import org.example.turnos.servicios.ILugarServicio;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LugarServicio implements ILugarServicio {

    @Autowired
    private ILugarRepositorio lugarRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    
    
    //Metodos HTML 
    
    @Override
    public LugarDTO agregarLugar(LugarDTO dto) {
        if (lugarRepositorio.existsByDireccion(dto.direccion())) {
            throw new DireccionLugarDuplicadaException("Ya existe un lugar con la dirección: " + dto.direccion());
        }
        try {
            Lugar lugar = new Lugar();
            lugar.setDireccion(dto.direccion());

            Lugar guardado = lugarRepositorio.save(lugar);
            return new LugarDTO(guardado.getIdLugar(), guardado.getDireccion());

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el lugar: " + e.getMessage());
        }
    }

    @Override
    public LugarDTO traerLugarPorDireccion(String direccion) {
        try {
            Lugar lugar = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + direccion));

            return new LugarDTO(lugar.getIdLugar(), lugar.getDireccion());

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el lugar: " + e.getMessage());
        }
    }

    @Override
    public List<LugarDTO> traerLugares() {
        try {
            return lugarRepositorio.findAll()
                    .stream()
                    .map(lugar -> new LugarDTO(lugar.getIdLugar(), lugar.getDireccion()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los lugares: " + e.getMessage());
        }
    }

    @Override
    public LugarDTO modificarLugar(String direccion, LugarDTO dto) {
        try {
            Lugar lugarExistente = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + direccion));

            lugarExistente.setDireccion(dto.direccion());

            Lugar actualizado = lugarRepositorio.save(lugarExistente);
            return new LugarDTO(actualizado.getIdLugar(), actualizado.getDireccion());

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo modificar el lugar: " + e.getMessage());
        }
    }

    @Override
    public void eliminarLugarPorDireccion(String direccion) {
        try {
            Lugar lugar = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("No se encontró lugar con dirección: " + direccion));

            lugarRepositorio.delete(lugar);

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo eliminar el lugar: " + e.getMessage());
        }
    }
    
    //Metodos Rest

    @Override
    public LugarDTO agregarLugarRest(LugarCrearDTO dto) {
        if (lugarRepositorio.existsByDireccion(dto.direccion())) {
            throw new DireccionLugarDuplicadaException(
                "Ya existe un lugar con la dirección: " + dto.direccion()
            );
        }

        try {
            Lugar lugar = new Lugar();
            lugar.setDireccion(dto.direccion());

            Lugar guardado = lugarRepositorio.save(lugar);
            return toDTO(guardado); 
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada(
                "No se pudo agregar el lugar: " + e.getMessage()
            );
        }
    }

    @Override
    public LugarDTO traerLugarPorDireccionRest(String direccion) {
        try {
            Lugar lugar = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + direccion));
            return toDTO(lugar);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el lugar: " + e.getMessage());
        }
    }

    @Override
    public List<LugarDTO> traerLugaresRest() {
        try {
            return lugarRepositorio.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los lugares: " + e.getMessage());
        }
    }

    @Override
    public LugarDTO modificarLugarRest(String direccion, LugarCrearDTO dto) {
        try {
            Lugar lugarExistente = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada(
                            "Lugar no encontrado con dirección: " + direccion));

            lugarExistente.setDireccion(dto.direccion());

            Lugar actualizado = lugarRepositorio.save(lugarExistente);
            return toDTO(actualizado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada(
                    "No se pudo modificar el lugar: " + e.getMessage());
        }
    }


    @Override
    public void eliminarLugarPorDireccionRest(String direccion) {
        try {
            Lugar lugar = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("No se encontró lugar con dirección: " + direccion));

            lugarRepositorio.delete(lugar);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo eliminar el lugar: " + e.getMessage());
        }
    }
    
 // Método para mapear de DTO → Entidad
    private Lugar toEntity(LugarDTO dto) {
        Lugar lugar = new Lugar();
        lugar.setIdLugar(dto.idLugar());
        lugar.setDireccion(dto.direccion());
        return lugar;
    }

    // Método para mapear de Entidad → DTO
    private LugarDTO toDTO(Lugar lugar) {
        return new LugarDTO(lugar.getIdLugar(), lugar.getDireccion());
    }
}
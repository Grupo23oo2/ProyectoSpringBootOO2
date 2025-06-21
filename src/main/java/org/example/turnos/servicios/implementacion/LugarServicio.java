package org.example.turnos.servicios.implementacion;

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

    @Override
    public LugarDTO agregarLugar(LugarDTO dto) {
        if (lugarRepositorio.existsByDireccion(dto.getDireccion())) {
            throw new DireccionLugarDuplicadaException("Ya existe un lugar con la dirección: " + dto.getDireccion());
        }

        try {
            Lugar lugar = modelMapper.map(dto, Lugar.class);
            Lugar guardado = lugarRepositorio.save(lugar);
            return modelMapper.map(guardado, LugarDTO.class);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el lugar: " + e.getMessage());
        }
    }

    @Override
    public LugarDTO traerLugar(Long id) {
    	try {
        Lugar lugar = lugarRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con id: " + id));
        return modelMapper.map(lugar, LugarDTO.class);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer el lugar" + e.getMessage());
        }
    }

    @Override
    public List<LugarDTO> traerLugares() {
    	try {
        return lugarRepositorio.findAll()
                .stream()
                .map(lugar -> modelMapper.map(lugar, LugarDTO.class))
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los lugares" + e.getMessage());
        }
    }

    @Override
    public LugarDTO modificarLugar(Long id, LugarDTO dto) {
    	try {
        Lugar lugarExistente = lugarRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con id: " + id));

        // Actualizo sólo lo necesario
        lugarExistente.setDireccion(dto.getDireccion());

        Lugar actualizado = lugarRepositorio.save(lugarExistente);
        return modelMapper.map(actualizado, LugarDTO.class);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar el lugar" + e.getMessage());
        }
    }

    @Override
    public void eliminarLugar(Long id) {
    	try {
        if (!lugarRepositorio.existsById(id)) {
            throw new MiExcepcionPersonalizada("Lugar no encontrado con id: " + id);
        }
        lugarRepositorio.deleteById(id);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el lugar" + e.getMessage());
        }
    }
}
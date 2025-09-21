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
        if (lugarRepositorio.existsByDireccion(dto.direccion())) {
            throw new DireccionLugarDuplicadaException("Ya existe un lugar con la dirección: " + dto.direccion());
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

    public LugarDTO traerLugarPorDireccion(String direccion) {
        try {
            Lugar lugar = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + direccion));
            return modelMapper.map(lugar, LugarDTO.class);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el lugar: " + e.getMessage());

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
    public LugarDTO modificarLugar(String direccion, LugarDTO dto) {
        try {
            Lugar lugarExistente = lugarRepositorio.findByDireccion(direccion)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + direccion));

            lugarExistente.setDireccion(dto.direccion());

            Lugar actualizado = lugarRepositorio.save(lugarExistente);
            return modelMapper.map(actualizado, LugarDTO.class);
        } catch (Exception e){
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
}
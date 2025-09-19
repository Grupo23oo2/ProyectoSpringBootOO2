package org.example.turnos.servicios;

import org.example.turnos.dtos.LugarDTO;

import java.util.List;

public interface ILugarServicio {
    LugarDTO agregarLugar(LugarDTO dto);

    LugarDTO traerLugarPorDireccion(String direccion);
    
    void eliminarLugarPorDireccion(String direccion);

    List<LugarDTO> traerLugares();

    LugarDTO modificarLugar(String direccion, LugarDTO dto);
}
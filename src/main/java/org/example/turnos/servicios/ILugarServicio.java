package org.example.turnos.servicios;

import org.example.turnos.dtos.LugarDTO;

import java.util.List;

public interface ILugarServicio {
    LugarDTO agregarLugar(LugarDTO dto);

    LugarDTO traerLugarPorDireccion(String direccion);

    List<LugarDTO> traerLugares();
    LugarDTO modificarLugar(String direccion, LugarDTO dto);
    void eliminarLugar(String direccion);
}
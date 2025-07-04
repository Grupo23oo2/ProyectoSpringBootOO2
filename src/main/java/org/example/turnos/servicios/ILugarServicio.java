package org.example.turnos.servicios;

import org.example.turnos.dtos.LugarDTO;

import java.util.List;

public interface ILugarServicio {
    LugarDTO agregarLugar(LugarDTO dto);
    LugarDTO traerLugar(Long id);
    List<LugarDTO> traerLugares();
    LugarDTO modificarLugar(Long id, LugarDTO dto);
    void eliminarLugar(Long id);
}

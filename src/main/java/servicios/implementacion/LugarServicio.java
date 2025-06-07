package servicios.implementacion;

import dtos.LugarDTO;
import modelo.Lugar;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ILugarRepositorio;
import servicios.ILugarServicio;

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
        Lugar lugar = modelMapper.map(dto, Lugar.class);
        Lugar guardado = lugarRepositorio.save(lugar);
        return modelMapper.map(guardado, LugarDTO.class);
    }

    @Override
    public LugarDTO traerLugar(Long id) {
        Lugar lugar = lugarRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + id));
        return modelMapper.map(lugar, LugarDTO.class);
    }

    @Override
    public List<LugarDTO> traerLugares() {
        return lugarRepositorio.findAll()
                .stream()
                .map(lugar -> modelMapper.map(lugar, LugarDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LugarDTO modificarLugar(Long id, LugarDTO dto) {
        Lugar lugarExistente = lugarRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + id));

        // Actualizo sólo lo necesario
        lugarExistente.setDireccion(dto.getDireccion());

        Lugar actualizado = lugarRepositorio.save(lugarExistente);
        return modelMapper.map(actualizado, LugarDTO.class);
    }

    @Override
    public void eliminarLugar(Long id) {
        if (!lugarRepositorio.existsById(id)) {
            throw new RuntimeException("Lugar no encontrado con id: " + id);
        }
        lugarRepositorio.deleteById(id);
    }
}
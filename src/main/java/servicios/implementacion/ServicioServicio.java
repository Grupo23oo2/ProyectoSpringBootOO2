package servicios.implementacion;

import dtos.ServicioDTO;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Lugar;
import modelo.Servicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.IClienteRepositorio;
import repositorios.IEmpleadoRepositorio;
import repositorios.ILugarRepositorio;
import repositorios.IServicioRepositorio;
import servicios.IServicioServicio;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioServicio implements IServicioServicio {

    @Autowired
    private IServicioRepositorio servicioRepositorio;

    @Autowired
    private ILugarRepositorio lugarRepositorio;

    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServicioDTO agregarServicio(ServicioDTO dto) {
        Servicio servicio = toEntity(dto);
        Servicio guardado = servicioRepositorio.save(servicio);
        return modelMapper.map(guardado, ServicioDTO.class);
    }

    @Override
    public ServicioDTO traerServicio(Long id) {
        Servicio servicio = servicioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
        return modelMapper.map(servicio, ServicioDTO.class);
    }

    @Override
    public List<ServicioDTO> traerServicios() {
        return servicioRepositorio.findAll()
                .stream()
                .map(servicio -> modelMapper.map(servicio, ServicioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ServicioDTO modificarServicio(Long id, ServicioDTO dto) {
        Servicio servicioExistente = servicioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

        // Actualizamos solo los campos que se pueden cambiar directamente
        servicioExistente.setFechaHoraInicio(dto.getFechaHoraInicio());
        servicioExistente.setFechaHoraFin(dto.getFechaHoraFin());

        Servicio actualizado = servicioRepositorio.save(servicioExistente);
        return modelMapper.map(actualizado, ServicioDTO.class);
    }

    @Override
    public void eliminarServicio(Long id) {
        if (!servicioRepositorio.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con id: " + id);
        }
        servicioRepositorio.deleteById(id);
    }

    // Conversión DTO -> entidad con ModelMapper y resolución manual de relaciones
    private Servicio toEntity(ServicioDTO dto) {
        Servicio servicio = modelMapper.map(dto, Servicio.class);

        Lugar lugar = lugarRepositorio.findById(dto.getIdLugarServicio())
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + dto.getIdLugarServicio()));
        servicio.setLugarServicio(lugar);

        Empleado empleado = empleadoRepositorio.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + dto.getIdEmpleado()));
        servicio.setEmpleado(empleado);

        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + dto.getIdCliente()));
        servicio.setCliente(cliente);

        return servicio;
    }
}

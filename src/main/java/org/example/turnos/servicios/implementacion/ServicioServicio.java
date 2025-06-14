package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.ServicioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Servicio;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IServicioRepositorio;
import org.example.turnos.servicios.IServicioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioServicio implements IServicioServicio {

	@Autowired
	private IServicioRepositorio servicioRepositorio;

	@Autowired
	private IClienteRepositorio clienteRepositorio;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ServicioDTO agregarServicio(ServicioDTO dto) {
		try {
		Servicio servicio = modelMapper.map(dto, Servicio.class);

		Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
				.orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con id: " + dto.getIdCliente()));

		servicio.setCliente(cliente);
		Servicio guardado = servicioRepositorio.save(servicio);

		return modelMapper.map(guardado, ServicioDTO.class);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo agregar el servicio" + e.getMessage());
        }
	}

	@Override
	public ServicioDTO traerServicio(Long id) {
		try {
		Servicio servicio = servicioRepositorio.findById(id)
				.orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con id: " + id));

		return modelMapper.map(servicio, ServicioDTO.class);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer el servicio" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServicios() {
		try {
		return servicioRepositorio.findAll().stream().map(s -> modelMapper.map(s, ServicioDTO.class))
				.collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los servicios" + e.getMessage());
        }
	}

	@Override
	public ServicioDTO modificarServicio(Long id, ServicioDTO dto) {
		try {
		Servicio servicio = servicioRepositorio.findById(id)
				.orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con id: " + id));

		// Solo modificamos fechas según tu comentario
		servicio.setFechaHoraInicio(dto.getFechaHoraInicio());
		servicio.setFechaHoraFin(dto.getFechaHoraFin());

		Servicio actualizado = servicioRepositorio.save(servicio);
		return modelMapper.map(actualizado, ServicioDTO.class);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar los servicios" + e.getMessage());
        }
	}

	@Override
	public void eliminarServicio(Long id) {
		try {
		if (!servicioRepositorio.existsById(id)) {
			throw new MiExcepcionPersonalizada("Servicio no encontrado con id: " + id);
		}
		servicioRepositorio.deleteById(id);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el servicio" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosEntreFechas(desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosDeClienteEntreFechas(Long idCliente, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosDeClienteEntreFechas(idCliente, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se traer servicios de clientes entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosDeEmpleadoEntreFechas(Long idEmpleado, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosDeEmpleadoEntreFechas(idEmpleado, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los servicios de empleados entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosPorLugarEntreFechas(Integer idLugar, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosPorLugarYFechas(Long.valueOf(idLugar), desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicio por lugar entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosPorPresencialYFechas(boolean presencial, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosPorPresencialYFechas(presencial, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios por presencial y fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosPorNombreClienteYFechas(String nombreCliente, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosPorNombreClienteYFechas(nombreCliente, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios por nombre del cliente y fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosPorRolEmpleadoYFechas(String rolEmpleado, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosPorRolEmpleadoYFechas(rolEmpleado, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios por el rol del empleado entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<ServicioDTO> traerServiciosPorDireccionLugarYFechas(String direccionLugar, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return servicioRepositorio.buscarServiciosPorDireccionLugarYFechas(direccionLugar, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios por la direccion del lugar y fechas" + e.getMessage());
        }
	}

	public List<ServicioDTO> obtenerServiciosPresenciales(boolean presencial) {
		try {
		return servicioRepositorio.obtenerServiciosPresenciales(presencial).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios presenciales" + e.getMessage());
        }
	}
	
	@Override
	public List<ServicioDTO> traerServiciosPorApellidoEmpleado(String apellido) {
		try {
	    List<Servicio> servicios = servicioRepositorio.findServiciosByApellidoEmpleado(apellido);
	    return servicios.stream()
	        .map(s -> new ServicioDTO(
	            s.getIdServicio(),
	            s.isPresencial(),
	            s.getLugarServicio() != null ? s.getLugarServicio().getIdLugar() : null,
	            s.getEmpleado() != null ? s.getEmpleado().getIdPersona() : null,
	            s.getCliente() != null ? s.getCliente().getIdPersona() : null,
	            s.getFechaHoraInicio(),
	            s.getFechaHoraFin()
	        ))
	        .toList();
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer servicios por apellido del empleado" + e.getMessage());
        }
	}
}
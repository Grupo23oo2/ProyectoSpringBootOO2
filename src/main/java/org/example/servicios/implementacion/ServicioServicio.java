package org.example.servicios.implementacion;

import org.example.dtos.ServicioDTO;
import org.example.modelo.Cliente;
import org.example.modelo.Servicio;
import org.example.repositorios.IClienteRepositorio;
import org.example.repositorios.IServicioRepositorio;
import org.example.servicios.IServicioServicio;
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
		Servicio servicio = modelMapper.map(dto, Servicio.class);

		Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + dto.getIdCliente()));

		servicio.setCliente(cliente);
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
		return servicioRepositorio.findAll().stream().map(s -> modelMapper.map(s, ServicioDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ServicioDTO modificarServicio(Long id, ServicioDTO dto) {
		Servicio servicio = servicioRepositorio.findById(id)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

		// Solo modificamos fechas según tu comentario
		servicio.setFechaHoraInicio(dto.getFechaHoraInicio());
		servicio.setFechaHoraFin(dto.getFechaHoraFin());

		Servicio actualizado = servicioRepositorio.save(servicio);
		return modelMapper.map(actualizado, ServicioDTO.class);
	}

	@Override
	public void eliminarServicio(Long id) {
		if (!servicioRepositorio.existsById(id)) {
			throw new RuntimeException("Servicio no encontrado con id: " + id);
		}
		servicioRepositorio.deleteById(id);
	}

	@Override
	public List<ServicioDTO> traerServiciosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosEntreFechas(desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosDeClienteEntreFechas(Long idCliente, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosDeClienteEntreFechas(idCliente, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosDeEmpleadoEntreFechas(Long idEmpleado, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosDeEmpleadoEntreFechas(idEmpleado, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosPorLugarEntreFechas(Integer idLugar, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosPorLugarYFechas(Long.valueOf(idLugar), desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosPorPresencialYFechas(boolean presencial, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosPorPresencialYFechas(presencial, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosPorNombreClienteYFechas(String nombreCliente, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosPorNombreClienteYFechas(nombreCliente, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosPorRolEmpleadoYFechas(String rolEmpleado, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosPorRolEmpleadoYFechas(rolEmpleado, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ServicioDTO> traerServiciosPorDireccionLugarYFechas(String direccionLugar, LocalDateTime desde,
			LocalDateTime hasta) {
		return servicioRepositorio.buscarServiciosPorDireccionLugarYFechas(direccionLugar, desde, hasta).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}

	public List<ServicioDTO> obtenerServiciosPresenciales(boolean presencial) {
		return servicioRepositorio.obtenerServiciosPresenciales(presencial).stream()
				.map(s -> modelMapper.map(s, ServicioDTO.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ServicioDTO> traerServiciosPorApellidoEmpleado(String apellido) {
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
	}
}
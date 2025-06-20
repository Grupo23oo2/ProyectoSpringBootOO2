package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.TurnoDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Lugar;
import org.example.turnos.modelo.Servicio;
import org.example.turnos.modelo.Turno;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.ILugarRepositorio;
import org.example.turnos.repositorios.IServicioRepositorio;
import org.example.turnos.repositorios.ITurnoRepositorio;
import org.example.turnos.servicios.ITurnoServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoServicio implements ITurnoServicio {

	@Autowired
	private ITurnoRepositorio turnoRepositorio;

	@Autowired
	private IClienteRepositorio clienteRepositorio;
	
	@Autowired
	private IEmpleadoRepositorio empleadoRepositorio;
	
	@Autowired
	private ILugarRepositorio lugarRepositorio;
	
	@Autowired
	private IServicioRepositorio servicioRepositorio;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TurnoDTO agregarTurno(TurnoDTO dto) {
	    try {
	        Turno turno = modelMapper.map(dto, Turno.class);

	        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
	                .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con id: " + dto.getIdCliente()));
	        turno.setCliente(cliente);

	        Empleado empleado = empleadoRepositorio.findById(dto.getIdEmpleado())
	                .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con id: " + dto.getIdEmpleado()));
	        turno.setEmpleado(empleado);

	        Lugar lugar = lugarRepositorio.findById(dto.getIdLugarTurno())
	                .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con id: " + dto.getIdLugarTurno()));
	        turno.setLugarTurno(lugar);

	        if (dto.getIdServicio() != null) {
	            Servicio servicio = servicioRepositorio.findById(dto.getIdServicio())
	                .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con id: " + dto.getIdServicio()));
	            turno.setServicio(servicio);
	        } else {
	            turno.setServicio(null);
	        }

	        Turno guardado = turnoRepositorio.save(turno);
	        return modelMapper.map(guardado, TurnoDTO.class);

	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo agregar el turno: " + e.getMessage());
	    }
	}

	@Override
	public TurnoDTO traerTurno(Long id) {
		try {
		Turno turno = turnoRepositorio.findById(id)
				.orElseThrow(() -> new MiExcepcionPersonalizada("Turno no encontrado con id: " + id));

		return modelMapper.map(turno, TurnoDTO.class);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer el turno" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnos() {
		try {
		return turnoRepositorio.findAll().stream().map(s -> modelMapper.map(s, TurnoDTO.class))
				.collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos" + e.getMessage());
        }
	}

	@Override
	public TurnoDTO modificarTurno(Long id, TurnoDTO dto) {
		try {
		Turno turno = turnoRepositorio.findById(id)
				.orElseThrow(() -> new MiExcepcionPersonalizada("Turno no encontrado con id: " + id));

		// Solo modificamos fechas según tu comentario
		turno.setFechaHoraInicio(dto.getFechaHoraInicio());
		turno.setFechaHoraFin(dto.getFechaHoraFin());

		Turno actualizado = turnoRepositorio.save(turno);
		return modelMapper.map(actualizado, TurnoDTO.class);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar los turnos" + e.getMessage());
        }
	}

	@Override
	public void eliminarTurno(Long id) {
		try {
		if (!turnoRepositorio.existsById(id)) {
			throw new MiExcepcionPersonalizada("Turno no encontrado con id: " + id);
		}
		turnoRepositorio.deleteById(id);
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el turno" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosEntreFechas(desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer turnos entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosDeClienteEntreFechas(Long idCliente, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosDeClienteEntreFechas(idCliente, desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos de clientes entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosDeEmpleadoEntreFechas(Long idEmpleado, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosDeEmpleadoEntreFechas(idEmpleado, desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos de empleados entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosPorLugarEntreFechas(Integer idLugar, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosPorLugarYFechas(Long.valueOf(idLugar), desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer turno por lugar entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosPorPresencialYFechas(boolean presencial, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosPorPresencialYFechas(presencial, desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos por presencial y fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosPorNombreClienteYFechas(String nombreCliente, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosPorNombreClienteYFechas(nombreCliente, desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos por nombre del cliente y fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosPorRolEmpleadoYFechas(String rolEmpleado, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosPorRolEmpleadoYFechas(rolEmpleado, desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos por el rol del empleado entre fechas" + e.getMessage());
        }
	}

	@Override
	public List<TurnoDTO> traerTurnosPorDireccionLugarYFechas(String direccionLugar, LocalDateTime desde,
			LocalDateTime hasta) {
		try {
		return turnoRepositorio.buscarTurnosPorDireccionLugarYFechas(direccionLugar, desde, hasta).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos por la direccion del lugar y fechas" + e.getMessage());
        }
	}

	public List<TurnoDTO> obtenerTurnosPresenciales(boolean presencial) {
		try {
		return turnoRepositorio.obtenerTurnosPresenciales(presencial).stream()
				.map(s -> modelMapper.map(s, TurnoDTO.class)).collect(Collectors.toList());
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos presenciales" + e.getMessage());
        }
	}
	
	@Override
	public List<TurnoDTO> traerTurnosPorApellidoEmpleado(String apellido) {
		try {
	    List<Turno> turnos = turnoRepositorio.findTurnosByApellidoEmpleado(apellido);
	    return turnos.stream()
	        .map(s -> new TurnoDTO(
	            s.getIdTurno(),
	            s.isPresencial(),
	            s.getLugarTurno() != null ? s.getLugarTurno().getIdLugar() : null,
	            s.getEmpleado() != null ? s.getEmpleado().getIdPersona() : null,
	            s.getCliente() != null ? s.getCliente().getIdPersona() : null,
	            s.getFechaHoraInicio(),
	            s.getFechaHoraFin(),
	            s.getServicio().getIdServicio()
	        ))
	        .toList();
		} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los turnos por apellido del empleado" + e.getMessage());
        }
	}
}
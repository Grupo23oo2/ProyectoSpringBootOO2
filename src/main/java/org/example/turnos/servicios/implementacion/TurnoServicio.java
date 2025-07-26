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

import java.time.*;
import java.util.ArrayList;

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
	        Turno turno = new Turno();
	        turno.setPresencial(dto.isPresencial());
	        turno.setFechaHoraInicio(dto.getFechaHoraInicio());
	        turno.setDuracionMinutos(dto.getDuracionMinutos());

	        // Buscar cliente por CUIT
	        Cliente cliente = clienteRepositorio.findByCuit(dto.getCuitCliente())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con CUIT: " + dto.getCuitCliente()));
	        turno.setCliente(cliente);

	        // Buscar empleado por DNI
	        Empleado empleado = empleadoRepositorio.findByDni(dto.getDniEmpleado())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dto.getDniEmpleado()));
	        turno.setEmpleado(empleado);

	        // Buscar lugar por dirección
	        Lugar lugar = lugarRepositorio.findByDireccion(dto.getDireccionLugar())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + dto.getDireccionLugar()));
	        turno.setLugarTurno(lugar);

	        // Buscar servicio por descripción (opcional)
	        if (dto.getDescripcionServicio() != null && !dto.getDescripcionServicio().isBlank()) {
	            Servicio servicio = servicioRepositorio.findByDescripcion(dto.getDescripcionServicio())
	                .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con descripción: " + dto.getDescripcionServicio()));
	            turno.setServicio(servicio);
	        } else {
	            turno.setServicio(null);
	        }

	        Turno guardado = turnoRepositorio.save(turno);

	        // Devolver DTO actualizado
	        return new TurnoDTO(
	            guardado.getIdTurno(),
	            guardado.isPresencial(),
	            guardado.getLugarTurno().getDireccion(),
	            guardado.getEmpleado().getDni(),
	            guardado.getCliente().getCuit(),
	            guardado.getFechaHoraInicio(),
	            guardado.getServicio() != null ? guardado.getServicio().getDescripcion() : null,
	            guardado.getDuracionMinutos()
	        );

	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo agregar el turno: " + e.getMessage());
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
	        
	        // Actualizar campo presencial
	        turno.setPresencial(dto.isPresencial());

	        // Actualizar fecha y hora inicio
	        turno.setFechaHoraInicio(dto.getFechaHoraInicio());

	        // Actualizar duración (suponiendo que Turno tenga un campo duración en minutos)
	        turno.setDuracionMinutos(dto.getDuracionMinutos());

	        // Buscar y setear Lugar por dirección
	        Lugar lugar = lugarRepositorio.findByDireccion(dto.getDireccionLugar())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + dto.getDireccionLugar()));
	        turno.setLugarTurno(lugar);

	        // Buscar y setear Empleado por DNI
	        Empleado empleado = empleadoRepositorio.findByDni(dto.getDniEmpleado())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dto.getDniEmpleado()));
	        turno.setEmpleado(empleado);

	        // Buscar y setear Cliente por CUIT
	        Cliente cliente = clienteRepositorio.findByCuit(dto.getCuitCliente())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con CUIT: " + dto.getCuitCliente()));
	        turno.setCliente(cliente);

	        // Buscar y setear Servicio por descripción
	        Servicio servicio = servicioRepositorio.findByDescripcion(dto.getDescripcionServicio())
	            .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con descripción: " + dto.getDescripcionServicio()));
	        turno.setServicio(servicio);

	        Turno actualizado = turnoRepositorio.save(turno);
	        return modelMapper.map(actualizado, TurnoDTO.class);

	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo modificar el turno: " + e.getMessage());
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

	public List<TurnoDTO> traerTurnosDeClientePorCuitEntreFechas(String cuit, LocalDateTime desde, LocalDateTime hasta) {
	    try {
	        return turnoRepositorio.buscarTurnosDeClientePorCuitEntreFechas(cuit, desde, hasta).stream()
	                .map(t -> modelMapper.map(t, TurnoDTO.class))
	                .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo traer los turnos del cliente por CUIT entre fechas: " + e.getMessage());
	    }
	}


	@Override
	public List<TurnoDTO> traerTurnosDeEmpleadoPorDniEntreFechas(String dni, LocalDateTime desde, LocalDateTime hasta) {
	    try {
	        return turnoRepositorio.buscarTurnosDeEmpleadoPorDniEntreFechas(dni, desde, hasta).stream()
	                .map(t -> modelMapper.map(t, TurnoDTO.class))
	                .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo traer los turnos del empleado por DNI entre fechas: " + e.getMessage());
	    }
	}


	@Override
	public List<TurnoDTO> traerTurnosPorDireccionEntreFechas(String direccion, LocalDateTime desde, LocalDateTime hasta) {
	    try {
	        return turnoRepositorio.buscarTurnosPorDireccionYFechas(direccion, desde, hasta).stream()
	                .map(t -> modelMapper.map(t, TurnoDTO.class))
	                .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo traer turnos por dirección entre fechas: " + e.getMessage());
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
	            .map(s -> {
	                TurnoDTO dto = new TurnoDTO();
	                dto.setIdTurno(s.getIdTurno());
	                dto.setPresencial(s.isPresencial());
	                dto.setDireccionLugar(s.getLugarTurno() != null ? s.getLugarTurno().getDireccion() : null);
	                dto.setDniEmpleado(s.getEmpleado() != null ? s.getEmpleado().getDni() : null);
	                dto.setCuitCliente(s.getCliente() != null ? s.getCliente().getCuit() : null);
	                dto.setFechaHoraInicio(s.getFechaHoraInicio());
	                dto.setDescripcionServicio(s.getServicio() != null ? s.getServicio().getDescripcion() : null);
	                dto.setDuracionMinutos(s.getDuracionMinutos());
	                return dto;
	            })
	            .toList();
	    } catch (Exception e) {
	        throw new MiExcepcionPersonalizada("No se pudo traer los turnos por apellido del empleado: " + e.getMessage());
	    }
	}


	
	@Override
	public List<LocalDate> obtenerDiasProximos(int cantidadDias) {
	    List<LocalDate> dias = new ArrayList<>();
	    LocalDate hoy = LocalDate.now();
	    for (int i = 0; i < cantidadDias; i++) {
	        dias.add(hoy.plusDays(i));
	    }
	    return dias;
	}

	@Override
	public List<String> obtenerHorasDisponiblesFijas() {
	    List<String> horas = new ArrayList<>();
	    LocalTime inicio = LocalTime.of(8, 0);
	    LocalTime fin = LocalTime.of(18, 0);

	    while (!inicio.isAfter(fin)) {
	        horas.add(inicio.toString());
	        inicio = inicio.plusMinutes(30);
	    }

	    return horas;
	}
	
	

}
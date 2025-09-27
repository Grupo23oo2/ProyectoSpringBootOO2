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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    
    @Override
    public TurnoDTO agregarTurno(TurnoDTO dto) {
        try {
            Turno turno = new Turno();
            turno.setPresencial(dto.presencial());
            turno.setFechaHoraInicio(dto.fechaHoraInicio());
            turno.setDuracionMinutos(dto.duracionMinutos());

            Cliente cliente = clienteRepositorio.findByCuit(dto.cuitCliente())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con CUIT: " + dto.cuitCliente()));
            turno.setCliente(cliente);

            Empleado empleado = empleadoRepositorio.findByDni(dto.dniEmpleado())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dto.dniEmpleado()));
            turno.setEmpleado(empleado);

            Lugar lugar = lugarRepositorio.findByDireccion(dto.direccionLugar())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + dto.direccionLugar()));
            turno.setLugarTurno(lugar);

            if (dto.descripcionServicio() != null && !dto.descripcionServicio().isBlank()) {
                Servicio servicio = servicioRepositorio.findByDescripcion(dto.descripcionServicio())
                        .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con descripción: " + dto.descripcionServicio()));
                turno.setServicio(servicio);
            } else {
                turno.setServicio(null);
            }

            Turno guardado = turnoRepositorio.save(turno);

            return mapToDTO(guardado);

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el turno: " + e.getMessage());
        }
    }

    @Override
    public TurnoDTO modificarTurno(Long id, TurnoDTO dto) {
        try {
            Turno turno = turnoRepositorio.findById(id)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Turno no encontrado con id: " + id));

            turno.setPresencial(dto.presencial());
            turno.setFechaHoraInicio(dto.fechaHoraInicio());
            turno.setDuracionMinutos(dto.duracionMinutos());

            Lugar lugar = lugarRepositorio.findByDireccion(dto.direccionLugar())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Lugar no encontrado con dirección: " + dto.direccionLugar()));
            turno.setLugarTurno(lugar);

            Empleado empleado = empleadoRepositorio.findByDni(dto.dniEmpleado())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dto.dniEmpleado()));
            turno.setEmpleado(empleado);

            Cliente cliente = clienteRepositorio.findByCuit(dto.cuitCliente())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con CUIT: " + dto.cuitCliente()));
            turno.setCliente(cliente);

            if (dto.descripcionServicio() != null && !dto.descripcionServicio().isBlank()) {
                Servicio servicio = servicioRepositorio.findByDescripcion(dto.descripcionServicio())
                        .orElseThrow(() -> new MiExcepcionPersonalizada("Servicio no encontrado con descripción: " + dto.descripcionServicio()));
                turno.setServicio(servicio);
            } else {
                turno.setServicio(null);
            }

            Turno actualizado = turnoRepositorio.save(turno);

            return mapToDTO(actualizado);

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
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo eliminar el turno: " + e.getMessage());
        }
    }

    @Override
    public List<TurnoDTO> traerTurnos() {
        List<Turno> turnos = turnoRepositorio.findAll();
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosEntreFechas(desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosDeClientePorCuitEntreFechas(String cuit, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosDeClientePorCuitEntreFechas(cuit, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosDeEmpleadoPorDniEntreFechas(String dni, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosDeEmpleadoPorDniEntreFechas(dni, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosPorDireccionEntreFechas(String direccion, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosPorDireccionYFechas(direccion, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosPorPresencialYFechas(boolean presencial, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosPorPresencialYFechas(presencial, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosPorNombreClienteYFechas(String nombreCliente, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosPorNombreClienteYFechas(nombreCliente, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosPorRolEmpleadoYFechas(String rolEmpleado, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosPorRolEmpleadoYFechas(rolEmpleado, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosPorDireccionLugarYFechas(String direccionLugar, LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepositorio.buscarTurnosPorDireccionLugarYFechas(direccionLugar, desde, hasta);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> obtenerTurnosPresenciales(boolean presencial) {
        List<Turno> turnos = turnoRepositorio.obtenerTurnosPresenciales(presencial);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDTO> traerTurnosPorApellidoEmpleado(String apellido) {
        List<Turno> turnos = turnoRepositorio.findTurnosByApellidoEmpleado(apellido);
        return turnos.stream().map(this::mapToDTO).toList();
    }

    //mapeo manual
    private TurnoDTO mapToDTO(Turno t) {
        return new TurnoDTO(
                t.getIdTurno(),
                t.isPresencial(),
                t.getLugarTurno() != null ? t.getLugarTurno().getDireccion() : null,
                t.getEmpleado() != null ? t.getEmpleado().getDni() : null,
                t.getCliente() != null ? t.getCliente().getCuit() : null,
                t.getFechaHoraInicio(),
                t.getServicio() != null ? t.getServicio().getDescripcion() : null,
                t.getDuracionMinutos()
        );
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
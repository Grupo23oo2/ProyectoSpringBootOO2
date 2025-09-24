package org.example.turnos.controlador;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.servicios.IEmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoRestController {

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    // Traer todos los empleados
    @GetMapping("/todos")
    public ResponseEntity<List<EmpleadoDTO>> traerTodos() {
        List<EmpleadoDTO> empleados = empleadoServicio.traerEmpleados();
        return ResponseEntity.ok(empleados);
    }

    // Buscar por DNI
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorDni(@RequestParam String dni) {
        try {
            EmpleadoDTO empleado = empleadoServicio.traerEmpleadoPorDni(dni);
            return ResponseEntity.ok(empleado);
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar por rol
    @GetMapping("/buscar-por-rol")
    public ResponseEntity<List<EmpleadoDTO>> buscarPorRol(@RequestParam String rol) {
        List<EmpleadoDTO> empleados = empleadoServicio.empleadosPorRol(rol);
        return ResponseEntity.ok(empleados);
    }

    // Buscar por fecha de inicio
    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<List<EmpleadoDTO>> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<EmpleadoDTO> empleados = empleadoServicio.empleadosPorFechaInicio(fecha);
        return ResponseEntity.ok(empleados);
    }

    // Agregar empleado
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarEmpleado(@RequestBody EmpleadoDTO dto) {
        try {
            EmpleadoDTO agregado = empleadoServicio.agregarEmpleado(dto);
            return ResponseEntity.ok(agregado);
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Modificar empleado
    @PutMapping("/modificar/{dniOriginal}")
    public ResponseEntity<?> modificarEmpleado(@PathVariable String dniOriginal,
                                               @RequestBody EmpleadoDTO dto) {
        try {
            EmpleadoDTO actualizado = empleadoServicio.modificarEmpleadoPorDni(dniOriginal, dto);
            return ResponseEntity.ok(actualizado);
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar empleado
    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable String dni) {
        try {
            empleadoServicio.eliminarEmpleadoPorDni(dni);
            return ResponseEntity.ok("Empleado eliminado con éxito (DNI: " + dni + ")");
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

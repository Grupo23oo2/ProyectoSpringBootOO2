package org.example.turnos.controlador;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.dtos.EmpleadoUsuarioDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.servicios.IEmpleadoServicio;
import org.example.turnos.servicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoRestController {
	
	@Autowired
    private IEmpleadoServicio empleadoServicio;

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;



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

    
 // Alta de empleado + usuario
    @PostMapping("/alta-completa")
    public ResponseEntity<?> altaEmpleadoYUsuario(@RequestBody EmpleadoUsuarioDTO dto) {
        try {
            Map<String, Object> result = empleadoServicio.altaEmpleadoYUsuario(dto);
            return ResponseEntity.ok(result);
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al registrar empleado y usuario: " + e.getMessage());
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

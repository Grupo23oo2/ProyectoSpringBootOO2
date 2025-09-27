package org.example.turnos.controlador;

import java.util.List;

import org.example.turnos.dtos.LugarCrearDTO;
import org.example.turnos.dtos.LugarDTO;
import org.example.turnos.servicios.ILugarServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lugares")
public class LugarRestController {

    private final ILugarServicio lugarServicio;

    @Autowired
    public LugarRestController(ILugarServicio lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

 // POST → Agregar lugar
    @PostMapping
    public ResponseEntity<LugarDTO> agregarLugar(@RequestBody LugarCrearDTO dto) {
        LugarDTO nuevo = lugarServicio.agregarLugarRest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }


    // GET → Traer todos los lugares
    @GetMapping
    public ResponseEntity<List<LugarDTO>> traerLugares() {
        return ResponseEntity.ok(lugarServicio.traerLugaresRest());
    }

    // GET → Traer lugar por dirección
    @GetMapping("/{direccion}")
    public ResponseEntity<LugarDTO> traerLugarPorDireccion(@PathVariable String direccion) {
        return ResponseEntity.ok(lugarServicio.traerLugarPorDireccionRest(direccion));
    }

 // PUT → Modificar lugar por dirección
    @PutMapping("/{direccion}")
    public ResponseEntity<LugarDTO> modificarLugar(@PathVariable String direccion,
                                                   @RequestBody LugarCrearDTO dto) {
        LugarDTO actualizado = lugarServicio.modificarLugarRest(direccion, dto);
        return ResponseEntity.ok(actualizado);
    }


    // DELETE → Eliminar lugar por dirección
    @DeleteMapping("/{direccion}")
    public ResponseEntity<Void> eliminarLugar(@PathVariable String direccion) {
        lugarServicio.eliminarLugarPorDireccionRest(direccion);
        return ResponseEntity.noContent().build();
    }
}

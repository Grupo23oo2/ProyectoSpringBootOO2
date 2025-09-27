package org.example.turnos.controlador;

import org.example.turnos.dtos.ClienteCreateDTO;
import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoCreateDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.servicios.IClienteServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestControlador {

    private final IClienteServicio clienteServicio;

    public ClienteRestControlador(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    //CREAR CLIENTE
    @PostMapping
    public ResponseEntity<ClienteDTO> agregarCliente(@RequestBody ClienteCreateDTO dto) {
        return ResponseEntity.ok(clienteServicio.agregarClienteAPI(dto));
    }

    //TRAER CLIENTE POR DNI
    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteDTO> traerClientePorDni(@PathVariable String dni) {
        Optional<ClienteDTO> clienteOpt = clienteServicio.traerClientePorDniAPI(dni);
        return clienteOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con DNI: " + dni));
    }

    //TRAER TODOS LOS CLIENTES
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> traerClientes() {
        return ResponseEntity.ok(clienteServicio.traerClientesAPI());
    }

    //MODIFICAR CLIENTE POR DNI
    @PutMapping("/dni/{dniOriginal}")
    public ResponseEntity<ClienteDTO> modificarCliente(
            @PathVariable String dniOriginal,
            @RequestBody ClienteCreateDTO dto
    ) {
        Optional<ClienteDTO> modificadoOpt = clienteServicio.modificarClientePorDniAPI(dniOriginal, dto);
        return modificadoOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MiExcepcionPersonalizada(
                        "No se pudo modificar cliente con DNI: " + dniOriginal));
    }

    //ELIMINAR CLIENTE POR DNI
    @DeleteMapping("/dni/{dni}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String dni) {
        clienteServicio.eliminarClientePorDniAPI(dni);
        return ResponseEntity.noContent().build();
    }

    //CLIENTES POR ROL
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<ClienteDTO>> clientesPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(clienteServicio.clientesPorRolAPI(rol));
    }

    //TODOS LOS CLIENTES POR CUIT
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<List<ClienteDTO>> findAllByCuit(@PathVariable String cuit) {
        return ResponseEntity.ok(clienteServicio.findAllByCuitAPI(cuit));
    }

    //CLIENTE POR CUIT
    @GetMapping("/buscar-cuit/{cuit}")
    public ResponseEntity<ClienteDTO> findByCuit(@PathVariable String cuit) {
        return ResponseEntity.ok(clienteServicio.findByCuitAPI(cuit));
    }

    //CONTACTO POR CUIT
    @GetMapping("/contacto/{cuit}")
    public ResponseEntity<ContactoCreateDTO> buscarContactoPorCuit(@PathVariable String cuit) {
        return ResponseEntity.ok(clienteServicio.buscarContactoPorCuitAPI(cuit));
    }
}

package org.example.turnos.servicios;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {

    public ClienteDTO agregarCliente(ClienteDTO dto);

    public Optional<ClienteDTO> traerClientePorId(Long id);

    public List<ClienteDTO> traerClientes();

    public Optional<ClienteDTO> modificarCliente(Long id, ClienteDTO dto);

    public void eliminarCliente(Long id);

    public List<ClienteDTO> clientesPorRol(String rol);
    
    public List<ClienteDTO> findAllByCuit(String cuit);
    
    public ClienteDTO findByCuit(String cuit);


    public ContactoDTO buscarContactoPorCuit(String cuit);
}

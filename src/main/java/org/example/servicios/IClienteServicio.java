package org.example.servicios;

import org.example.dtos.ClienteDTO;
import org.example.dtos.ContactoDTO;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {

    public ClienteDTO agregarCliente(ClienteDTO dto);

    public Optional<ClienteDTO> traerClientePorId(Long id);

    public List<ClienteDTO> traerClientes();

    public Optional<ClienteDTO> modificarCliente(Long id, ClienteDTO dto);

    public void eliminarCliente(Long id);

    public List<ClienteDTO> clientesPorRol(String rol);
    public List<ClienteDTO> clientesPorCuit(String cuit);

    public ContactoDTO buscarContactoPorCuit(String cuit);
}

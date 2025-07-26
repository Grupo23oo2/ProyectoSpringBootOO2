package org.example.turnos.servicios;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {

    public ClienteDTO agregarCliente(ClienteDTO dto);

    public Optional<ClienteDTO> traerClientePorDni(String dni);

    public List<ClienteDTO> traerClientes();
    
    public Optional<ClienteDTO> modificarClientePorDni(String dniOriginal, ClienteDTO dto);
    //public Optional<ClienteDTO> modificarClienteYContactoPorDni(String dniOriginal, ClienteDTO clienteDTO, ContactoDTO contactoDTO);
    
    void eliminarClientePorDni(String dni);

    public List<ClienteDTO> clientesPorRol(String rol);
    public List<ClienteDTO> clientesPorCuit(String cuit);

    public ContactoDTO buscarContactoPorCuit(String cuit);
}

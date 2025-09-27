package org.example.turnos.servicios;

import org.example.turnos.dtos.ClienteCreateDTO;
import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoCreateDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Contacto;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {
	//MÉTODOS HTML
    public ClienteDTO agregarCliente(ClienteDTO dto);

    public Optional<ClienteDTO> traerClientePorDni(String dni);

    public List<ClienteDTO> traerClientes();
    
    public Optional<ClienteDTO> modificarClientePorDni(String dniOriginal, ClienteDTO dto);
    
    void eliminarClientePorDni(String dni);

    public List<ClienteDTO> clientesPorRol(String rol);
    
    public List<ClienteDTO> findAllByCuit(String cuit);
    
    public ClienteDTO findByCuit(String cuit);

    public ContactoDTO buscarContactoPorCuit(String cuit);
    
    public Cliente guardarClienteConContacto(Cliente cliente, Contacto contacto);
    
    //MÉTODOS API (nuevos)
    ClienteDTO agregarClienteAPI(ClienteCreateDTO dto);
    Optional<ClienteDTO> traerClientePorDniAPI(String dni);
    List<ClienteDTO> traerClientesAPI();
    Optional<ClienteDTO> modificarClientePorDniAPI(String dniOriginal, ClienteCreateDTO dto);
    void eliminarClientePorDniAPI(String dni);
    List<ClienteDTO> clientesPorRolAPI(String rol);
    List<ClienteDTO> findAllByCuitAPI(String cuit);
    ClienteDTO findByCuitAPI(String cuit);
    ContactoCreateDTO buscarContactoPorCuitAPI(String cuit);
}

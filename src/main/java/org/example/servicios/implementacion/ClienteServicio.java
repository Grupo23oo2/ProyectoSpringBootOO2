package org.example.servicios.implementacion;

import org.example.dtos.ClienteDTO;
import org.example.dtos.ContactoDTO;
import org.example.excepciones.MiExcepcionPersonalizada;

import jakarta.persistence.EntityNotFoundException;
import org.example.modelo.Cliente;
import org.example.modelo.Contacto;
import org.example.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repositorios.IClienteRepositorio;
import org.example.repositorios.IContactoRepositorio;
import org.example.repositorios.IUsuarioRepositorio;
import org.example.servicios.IClienteServicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServicio implements IClienteServicio {

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IContactoRepositorio contactoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    public ClienteDTO agregarCliente(ClienteDTO dto) {
    	try {
        Cliente cliente = toEntity(dto);
        Cliente guardado = clienteRepositorio.save(cliente);
        return toDTO(guardado);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo agregar el cliente" + e.getMessage());
        }
    }

    public Optional<ClienteDTO> traerClientePorId(Long id) {
    	try {
        return clienteRepositorio.findById(id).map(this::toDTO);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer el cliente" + e.getMessage());
        }
    }

    public List<ClienteDTO> traerClientes() {
    	try {
        return clienteRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los clientes" + e.getMessage());
        }
    }

    public Optional<ClienteDTO> modificarCliente(Long id, ClienteDTO dto) {
    	try {
        return clienteRepositorio.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(dto.getNombre());
            clienteExistente.setApellido(dto.getApellido());
            clienteExistente.setDni(dto.getDni());
            clienteExistente.setCuit(dto.getCuit());

            if (dto.getIdUsuario() != null) {
                Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
                clienteExistente.setUsuario(usuario);
            } else {
                clienteExistente.setUsuario(null);
            }

            if (dto.getIdContacto() != null) {
                Contacto contacto = contactoRepositorio.findById(dto.getIdContacto()).orElse(null);
                clienteExistente.setContacto(contacto);
            } else {
                clienteExistente.setContacto(null);
            }

            Cliente modificado = clienteRepositorio.save(clienteExistente);
            return toDTO(modificado);
        });
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar el cliente");
        }
    }

    public void eliminarCliente(Long id) {
    	try {
        Cliente cliente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró Cliente con ID: " + id));
        clienteRepositorio.delete(cliente);
        } catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el cliente" + e.getMessage());
        }
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);

        // Ajustamos los campos relacionados que no mapea automáticamente ModelMapper
        dto.setIdUsuario(cliente.getUsuario() != null ? cliente.getUsuario().getIdUsuario() : null);
        dto.setIdContacto(cliente.getContacto() != null ? cliente.getContacto().getIdContacto() : null);

        return dto;
    
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);

        // Asignamos manualmente las relaciones
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
            cliente.setUsuario(usuario);
        }

        if (dto.getIdContacto() != null) {
            Contacto contacto = contactoRepositorio.findById(dto.getIdContacto()).orElse(null);
            cliente.setContacto(contacto);
        }

        return cliente;
    }
    
    @Override
    public List<ClienteDTO> clientesPorRol(String rol) {
    	try {
        return clienteRepositorio.findByRol(rol)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los clientes por rol" + e.getMessage());
        }
    }

    @Override
    public List<ClienteDTO> clientesPorCuit(String cuit) {
    	try {
        return clienteRepositorio.findByCuit(cuit)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se traer los clientes por cuit" + e.getMessage());
        }
    }
    
    @Override
    public ContactoDTO buscarContactoPorCuit(String cuit) {
    	try {
        Contacto contacto = clienteRepositorio.findContactoByCuit(cuit);
        return modelMapper.map(contacto, ContactoDTO.class);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer el contacto por cuit" + e.getMessage());
        }
    }
}

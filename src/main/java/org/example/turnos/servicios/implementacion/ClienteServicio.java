package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.ClienteCreateDTO;
import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoCreateDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.excepciones.CuitClienteDuplicadoException;
import org.example.turnos.excepciones.DniClienteDuplicadoException;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;

import jakarta.persistence.EntityNotFoundException;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Contacto;
import org.example.turnos.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IContactoRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IClienteServicio;

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

    // -------------------------------
    // MÉTODOS PARA HTML
    // -------------------------------
    public ClienteDTO agregarCliente(ClienteDTO dto) {
        if (clienteRepositorio.existsByCuit(dto.cuit())) {
            throw new CuitClienteDuplicadoException("Ya existe un cliente con el CUIT: " + dto.cuit());
        }

        if (clienteRepositorio.existsByDni(dto.dni())) {
            throw new DniClienteDuplicadoException("Ya existe un cliente con el DNI: " + dto.dni());
        }

        try {
            Cliente cliente = toEntity(dto);
            Cliente guardado = clienteRepositorio.save(cliente);
            return toDTO(guardado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el cliente: " + e.getMessage());
        }
    }

    public Optional<ClienteDTO> traerClientePorDni(String dni) {
    	try {
        return clienteRepositorio.findByDni(dni).map(this::toDTO);
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

    public Optional<ClienteDTO> modificarClientePorDni(String dniOriginal, ClienteDTO dto) {
    	try {
    	return clienteRepositorio.findByDni(dniOriginal).map(clienteExistente -> {
            clienteExistente.setNombre(dto.nombre());
            clienteExistente.setApellido(dto.apellido());
            clienteExistente.setDni(dto.dni());
            clienteExistente.setCuit(dto.cuit());

            if (dto.idUsuario() != null) {
                Usuario usuario = usuarioRepositorio.findById(dto.idUsuario()).orElse(null);
                clienteExistente.setUsuario(usuario);
            } else {
                clienteExistente.setUsuario(null);
            }

            if (dto.idContacto() != null) {
                Contacto contacto = contactoRepositorio.findById(dto.idContacto()).orElse(null);
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
   
    public void eliminarClientePorDni(String dni) {
    	try {
    	Cliente cliente = clienteRepositorio.findByDni(dni)
    			.orElseThrow(() -> new EntityNotFoundException("No se encontró cliente con DNI: " + dni));
        clienteRepositorio.delete(cliente);
        } catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el cliente" + e.getMessage());
        }
    } 
    
    private ClienteDTO toDTO(Cliente cliente) {//nuevo para record
        return new ClienteDTO(
            cliente.getIdPersona(),
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getDni(),
            cliente.getUsuario() != null ? cliente.getUsuario().getIdUsuario() : null,
            cliente.getCuit(),
            cliente.getContacto() != null ? cliente.getContacto().getIdContacto() : null
        );
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);

        // Asignamos manualmente las relaciones
        if (dto.idUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.idUsuario()).orElse(null);
            cliente.setUsuario(usuario);
        }

        if (dto.idContacto() != null) {
            Contacto contacto = contactoRepositorio.findById(dto.idContacto()).orElse(null);
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
    public List<ClienteDTO> findAllByCuit(String cuit) {
    	try {
        return clienteRepositorio.findAllByCuit(cuit)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se traer los clientes por cuit" + e.getMessage());
        }
    }
    
    @Override
    public ClienteDTO findByCuit(String cuit) {
        Cliente cliente = clienteRepositorio.findByCuit(cuit)
            .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con CUIT: " + cuit));
        
        return toDTO(cliente);
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
    
    // -------------------------------
    // MÉTODOS API JSON
    // -------------------------------

    public ClienteDTO agregarClienteAPI(ClienteCreateDTO dto) {
        try {
            if (clienteRepositorio.existsByDni(dto.dni())) {
                throw new MiExcepcionPersonalizada("Ya existe un cliente con DNI: " + dto.dni());
            }
            if (clienteRepositorio.existsByCuit(dto.cuit())) {
                throw new MiExcepcionPersonalizada("Ya existe un cliente con CUIT: " + dto.cuit());
            }

            //Creamos primero el Cliente sin relaciones
            Cliente cliente = new Cliente();
            cliente.setNombre(dto.nombre());
            cliente.setApellido(dto.apellido());
            cliente.setDni(dto.dni());
            cliente.setCuit(dto.cuit());

            //Guardamos el cliente primero para que genere idPersona
            Cliente guardadoCliente = clienteRepositorio.save(cliente);

            //Creamos el Contacto y asignamos el mismo id que Cliente
            if (dto.contacto() != null) {
                Contacto contacto = new Contacto();
                contacto.setIdContacto(guardadoCliente.getIdPersona());
                contacto.setDireccion(dto.contacto().direccion());
                contacto.setEmail(dto.contacto().email());
                contacto.setTelefono(dto.contacto().telefono());
                guardadoCliente.setContacto(contacto);
            }

            //Creamos el Usuario y asignamos la relación
            if (dto.usuario() != null) {
                Usuario usuario = new Usuario();
                usuario.setEmail(dto.usuario().email());
                usuario.setNombreUsuario(dto.usuario().nombreUsuario());
                usuario.setContraseniaUsuario(dto.usuario().contraseniaUsuario());
                usuario.setRol(dto.usuario().rol());
                usuario.setEstado(true);
                usuario.setPersona(guardadoCliente);
                guardadoCliente.setUsuario(usuario);
            }

            //Guardamos nuevamente el cliente con contacto y usuario
            guardadoCliente = clienteRepositorio.save(guardadoCliente);

            return toDTO(guardadoCliente);

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo crear el cliente: " + e.getMessage());
        }
    }

    public Optional<ClienteDTO> traerClientePorDniAPI(String dni) {
        return clienteRepositorio.findByDni(dni)
                .map(this::toDTO);
    }

    public List<ClienteDTO> traerClientesAPI() {
        return clienteRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> modificarClientePorDniAPI(String dniOriginal, ClienteCreateDTO dto) {
        return clienteRepositorio.findByDni(dniOriginal).map(clienteExistente -> {
            //Modificamos el cliente
            clienteExistente.setNombre(dto.nombre());
            clienteExistente.setApellido(dto.apellido());
            clienteExistente.setDni(dto.dni());
            clienteExistente.setCuit(dto.cuit());

            //Modificamos o creamos el contacto
            if (dto.contacto() != null) {
                Contacto contacto = clienteExistente.getContacto();
                if (contacto == null) {
                    contacto = new Contacto();
                    contacto.setIdContacto(clienteExistente.getIdPersona()); // igual que el Cliente
                    clienteExistente.setContacto(contacto);
                }
                contacto.setDireccion(dto.contacto().direccion());
                contacto.setEmail(dto.contacto().email());
                contacto.setTelefono(dto.contacto().telefono());
            }

            //Modificamos o creamos el usuario
            if (dto.usuario() != null) {
                Usuario usuario = clienteExistente.getUsuario();
                if (usuario == null) {
                    usuario = new Usuario();
                    usuario.setPersona(clienteExistente);
                    clienteExistente.setUsuario(usuario);
                }
                usuario.setEmail(dto.usuario().email());
                usuario.setNombreUsuario(dto.usuario().nombreUsuario());
                usuario.setContraseniaUsuario(dto.usuario().contraseniaUsuario());
                usuario.setRol(dto.usuario().rol());
                usuario.setEstado(true);
            }

            Cliente modificado = clienteRepositorio.save(clienteExistente);
            return toDTO(modificado);
        });
    }

    public void eliminarClientePorDniAPI(String dni) {
        Cliente cliente = clienteRepositorio.findByDni(dni)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con DNI: " + dni));
        clienteRepositorio.delete(cliente);
    }

    public List<ClienteDTO> clientesPorRolAPI(String rol) {
        return clienteRepositorio.findByRol(rol)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> findAllByCuitAPI(String cuit) {
        return clienteRepositorio.findAllByCuit(cuit)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO findByCuitAPI(String cuit) {
        Cliente cliente = clienteRepositorio.findByCuit(cuit)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Cliente no encontrado con CUIT: " + cuit));
        return toDTO(cliente);
    }

    public ContactoCreateDTO buscarContactoPorCuitAPI(String cuit) {
        Contacto contacto = clienteRepositorio.findContactoByCuit(cuit);
        if (contacto == null) {
            throw new MiExcepcionPersonalizada("No se encontró contacto con CUIT: " + cuit);
        }
        return new ContactoCreateDTO(
            contacto.getDireccion(),
            contacto.getEmail(),
            contacto.getTelefono()
        );
    }
    
    private Cliente toEntityAPI(ClienteCreateDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setDni(dto.dni());
        cliente.setCuit(dto.cuit());

        //Creamos el Contacto
        if (dto.contacto() != null) {
            Contacto contacto = new Contacto();
            contacto.setDireccion(dto.contacto().direccion());
            contacto.setEmail(dto.contacto().email());
            contacto.setTelefono(dto.contacto().telefono());
            cliente.setContacto(contacto);
        }

        //Creamos el Usuario
        if (dto.usuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setEmail(dto.usuario().email());
            usuario.setNombreUsuario(dto.usuario().nombreUsuario());
            usuario.setContraseniaUsuario(dto.usuario().contraseniaUsuario());
            usuario.setRol(dto.usuario().rol());
            usuario.setEstado(true);
            usuario.setPersona(cliente);
            cliente.setUsuario(usuario);
        }

        return cliente;
    }
}

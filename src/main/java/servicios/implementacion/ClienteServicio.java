package servicios.implementacion;

import dtos.ClienteDTO;
import jakarta.persistence.EntityNotFoundException;
import modelo.Cliente;
import modelo.Contacto;
import modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.IClienteRepositorio;
import repositorios.IContactoRepositorio;
import repositorios.IUsuarioRepositorio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServicio {

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IContactoRepositorio contactoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    public ClienteDTO agregarCliente(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        Cliente guardado = clienteRepositorio.save(cliente);
        return toDTO(guardado);
    }

    public Optional<ClienteDTO> traerClientePorId(Long id) {
        return clienteRepositorio.findById(id).map(this::toDTO);
    }

    public List<ClienteDTO> traerClientes() {
        return clienteRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> modificarCliente(Long id, ClienteDTO dto) {
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
    }

    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró Cliente con ID: " + id));
        clienteRepositorio.delete(cliente);
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
}

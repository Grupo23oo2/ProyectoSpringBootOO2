package servicios;

import dtos.ClienteDTO;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {

    public ClienteDTO agregarCliente(ClienteDTO dto);

    public Optional<ClienteDTO> traerClientePorId(Long id);

    public List<ClienteDTO> traerClientes();

    public Optional<ClienteDTO> modificarCliente(Long id, ClienteDTO dto);

    public void eliminarCliente(Long id);

}

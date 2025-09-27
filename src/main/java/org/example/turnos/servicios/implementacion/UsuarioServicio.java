package org.example.turnos.servicios.implementacion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Persona;
import org.example.turnos.modelo.Usuario;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IEmailServicio;
import org.example.turnos.servicios.IUsuarioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements IUsuarioServicio {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private IEmailServicio emailServicio;

    @Override
    public UsuarioDTO agregarUsuario(UsuarioDTO dto) {
        try {
            Persona persona = buscarPersonaPorId(dto.idPersona());

            Usuario usuario = toEntity(dto, persona);
            
            LocalDateTime hora = LocalDateTime.now();
           // dto.fechaCreacion(hora); //no se puede usar por los record. Cada vez que aparece esto, aunque sea un solo atributo, hay que poner el constructor completo de la record
            dto = new UsuarioDTO(
                    dto.idUsuario(),
                    dto.nombreUsuario(),
                    dto.contraseniaUsuario(),
                    dto.estado(),
                    dto.idPersona(),
                    dto.email(),
                    dto.rol(),
                    hora//fechaCreacion actualizado como en la linea 48
                );
            
            Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
            String contenidoHtml = """
                    <html>
                    <body>
                        <h1 style='color: blue;'>Sistema de Turnos!</h1>
                        <p>Se registro <b>correctamente</b>.</p>
                    </body>
                    </html>
                    """;
            emailServicio.enviarCorreoHtml(dto.email(), "alta de usuario", contenidoHtml);//envio automatico del mail
            return toDTO(usuarioGuardado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el usuario: " + e.getMessage());
        }
    }

    private Persona buscarPersonaPorId(Long idPersona) {
        try {
            Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(idPersona);
            if (empleadoOpt.isPresent()) return empleadoOpt.get();

            Optional<Cliente> clienteOpt = clienteRepositorio.findById(idPersona);
            if (clienteOpt.isPresent()) return clienteOpt.get();

            throw new MiExcepcionPersonalizada("No se encontró Persona con ID: " + idPersona);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer la persona por id: " + e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> traerUsuarios() {
        try {
            return usuarioRepositorio.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los usuarios: " + e.getMessage());
        }
    }
    
    @Override
    public UsuarioDTO traerUsuarioPorEmail(String email) {//modificado para record. no se puede usar modelmapper porque no se puede setear, es inmutable
        try {
            Usuario usuario = usuarioRepositorio.findByEmail(email)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con email: " + email));

            Long idPersona = usuario.getPersona() != null ? usuario.getPersona().getIdPersona() : null;

            return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getContraseniaUsuario(),
                usuario.isEstado(),
                idPersona,
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getFechaCreacion()
            );
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el usuario: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<UsuarioDTO> traerUsuarioPorNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario)
                .map(this::toDTO); // mapeamos a DTO directamente
    }

    @Override
    public UsuarioDTO modificarUsuario(String email, UsuarioDTO dto) {
        try {
            Usuario usuarioExistente = usuarioRepositorio.findByEmail(email)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con email: " + email));

            if (dto.idPersona() != null) {
                Persona persona = buscarPersonaPorId(dto.idPersona());
                usuarioExistente.setPersona(persona);
            }

            usuarioExistente.setNombreUsuario(dto.nombreUsuario());
            usuarioExistente.setContraseniaUsuario(dto.contraseniaUsuario());
            usuarioExistente.setEstado(dto.estado());
            usuarioExistente.setEmail(dto.email());
            //usuarioExistente.setRol(dto.rol()); //al modificar NO queremos que pueda modificar el rol del usuario
            //usuarioExistente.setFechaCreacion(dto.fechaCreacion()); //al modificar NO queremos que pueda modificar el fecha de creacion del usuario

            Usuario usuarioModificado = usuarioRepositorio.save(usuarioExistente);
            return toDTO(usuarioModificado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo modificar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(String email) {
        try {
            Usuario usuario = usuarioRepositorio.findByEmail(email)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("No se encontró Usuario con email: " + email));
            usuarioRepositorio.delete(usuario);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo eliminar el usuario: " + e.getMessage());
        }
    }

    //metodo para HTML
    private UsuarioDTO toDTO(Usuario usuario) {//modificado para record
        //Obtenemos el idPersona si existe
        Long idPersona = usuario.getPersona() != null ? usuario.getPersona().getIdPersona() : null;

        //Construimos directamente el record
        return new UsuarioDTO(
            usuario.getIdUsuario(),
            usuario.getNombreUsuario(),
            usuario.getContraseniaUsuario(),
            usuario.isEstado(),
            idPersona,
            usuario.getEmail(),
            usuario.getRol(),
            usuario.getFechaCreacion()
        );
    }
    
    //metodo para la API
    public UsuarioDTO mapToDTO(Usuario usuario) {
        return toDTO(usuario); // llama internamente al private
    }
    
    //Mapeo manual de DTO a entidad para evitar problemas con Persona abstracta
    private Usuario toEntity(UsuarioDTO dto, Persona persona) {
        Usuario usuario = new Usuario();

        if (dto.idUsuario() != null) {
            usuario.setIdUsuario(dto.idUsuario());
        }
        usuario.setNombreUsuario(dto.nombreUsuario());
        usuario.setContraseniaUsuario(dto.contraseniaUsuario());
        usuario.setEstado(dto.estado());
        usuario.setEmail(dto.email());

        usuario.setRol(dto.rol());

        usuario.setPersona(persona);
        
        usuario.setRol(dto.rol());
        usuario.setFechaCreacion(dto.fechaCreacion());

        return usuario;
    }   
}
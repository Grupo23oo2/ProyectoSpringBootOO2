package org.example.turnos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.dtos.LugarDTO;
import org.example.turnos.dtos.RolUsuarioDTO;
import org.example.turnos.dtos.ServicioDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Contacto;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Lugar;
import org.example.turnos.modelo.RolUsuario;
import org.example.turnos.modelo.Servicio;
import org.example.turnos.modelo.Usuario;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IContactoRepositorio;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.ILugarRepositorio;
import org.example.turnos.repositorios.IRolUsuarioRepositorio;
import org.example.turnos.repositorios.IServicioRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IClienteServicio;
import org.example.turnos.servicios.IContactoServicio;
import org.example.turnos.servicios.IEmpleadoServicio;
import org.example.turnos.servicios.ILugarServicio;
import org.example.turnos.servicios.IRolUsuarioServicio;
import org.example.turnos.servicios.IServicioServicio;
import org.example.turnos.servicios.IUsuarioServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProyectoSpringBootOO2Application {
    public static void main(String[] args) {
    //	SpringApplication.run(ProyectoSpringBootOO2Application.class, args);
    	
    	ApplicationContext context = SpringApplication.run(ProyectoSpringBootOO2Application.class, args);
    	
        
       IRolUsuarioRepositorio rolUsuarioRepositorio = context.getBean(IRolUsuarioRepositorio.class);
        
        IClienteRepositorio clienteRepositorio = context.getBean(IClienteRepositorio.class);
        
        IEmpleadoRepositorio empleadoRepositorio = context.getBean(IEmpleadoRepositorio.class);
        
        IUsuarioRepositorio usuarioRepositorio = context.getBean(IUsuarioRepositorio.class);
        
        IContactoRepositorio contactoRepositorio = context.getBean(IContactoRepositorio.class);
        
        ILugarRepositorio lugarRepositorio = context.getBean(ILugarRepositorio.class);
         
        IServicioRepositorio servicioRepositorio = context.getBean(IServicioRepositorio.class);
        
        IEmpleadoServicio empleadoServicio = context.getBean(IEmpleadoServicio.class);
        
        IClienteServicio clienteServicio = context.getBean(IClienteServicio.class);
        
        IUsuarioServicio usuarioServicio = context.getBean(IUsuarioServicio.class);
        
        IRolUsuarioServicio rolUsuarioServicio = context.getBean(IRolUsuarioServicio.class);
        
        IContactoServicio contactoServicio = context.getBean(IContactoServicio.class);
        
        ILugarServicio lugarServicio = context.getBean(ILugarServicio.class);
        
        IServicioServicio servicioServicio = context.getBean(IServicioServicio.class);
        
        
        System.out.println("----- AGREGAR------");
        
        Empleado empleado = new Empleado("carla", "gonzalez", "1111111111", null, LocalDate.now(), new HashSet<>());
        empleado = empleadoRepositorio.save(empleado);
        
        Cliente cliente = new Cliente("carlos", "gutierrez", "2222222222",null, "4444444444", null, new HashSet<>()); 
        cliente = clienteRepositorio.save(cliente);
       
        Cliente cliente2 = new Cliente("ssss", "aaaa", "3333333333",null, "6666666666", null, new HashSet<>());
        Usuario usuario = new Usuario (cliente2, "peperino20", "123456", true, new HashSet<>());
   //     usuario.setIdUsuario(idCliente);   
        usuarioRepositorio.save(usuario);
        
        
        
        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setUsuario(usuario); // entidad, no ID
        rolUsuario.setRole("ADMIN");
        rolUsuario.setFechaCreacion(LocalDateTime.now());

        // Si querés dejar que @CreationTimestamp lo maneje, omití esta línea
    //    rolUsuario.setFechaCreacion(rolUsuarioDTO.getFechaCreacion());
        
        rolUsuarioRepositorio.save(rolUsuario);
       
        
        Contacto contacto = new Contacto();
        contacto.setIdContacto(cliente.getIdPersona()); // Debe coincidir con Cliente.idPersona
        contacto.setDireccion("Enrique Segobiano");
        contacto.setEmail("carlos@Hotmail.com");
        contacto.setTelefono("1515151515");
        
        contactoRepositorio.save(contacto);
        
        
        Lugar lugar = new Lugar();
        // No se setea el idLugar porque es autogenerado
        lugar.setDireccion("Calle falsa 123");
        
        lugarRepositorio.save(lugar);
      
        
        
        Servicio servicio = new Servicio();
        // ID no se setea porque se genera automáticamente
        servicio.setPresencial(true);
        servicio.setFechaHoraInicio(LocalDateTime.of(2025, 8, 10, 20, 00));
        servicio.setFechaHoraFin(LocalDateTime.of(2025, 8, 10, 21, 00));

        servicio.setLugarServicio(lugar);
        servicio.setEmpleado(empleado);
        servicio.setCliente(cliente);
        
        servicioRepositorio.save(servicio);
        
          
        System.out.println("----- TRAER POR ID------");
        
        System.out.println(empleadoServicio.traerEmpleadoPorId(1L));
        System.out.println(clienteServicio.traerClientePorId(2L));
        System.out.println(usuarioServicio.traerUsuarioPorId(3L));
        System.out.println(rolUsuarioServicio.traerRolUsuario(1L));
        System.out.println(contactoServicio.traerContacto(2L));
        System.out.println(lugarServicio.traerLugar(1L));
        System.out.println(servicioServicio.traerServicio(1L));
        
       
        System.out.println("----- TRAER TODO------");
        
        List<ClienteDTO> clientes = clienteServicio.traerClientes();
     // Imprimís los clientes
        clientes.forEach(clienteT -> System.out.println(clienteT));
        
        List<EmpleadoDTO> empleados = empleadoServicio.traerEmpleados();
        // Imprimís los clientes
        empleados.forEach(empleadoT -> System.out.println(empleadoT));
        
        List<UsuarioDTO> usuarios = usuarioServicio.traerUsuarios();
        // Imprimís los clientes
        usuarios.forEach(usuarioT -> System.out.println(usuarioT));
     
        List<RolUsuarioDTO> rolUsuarios = rolUsuarioServicio.traerRolesUsuarios();
        // Imprimís los clientes
        rolUsuarios.forEach(rolUsuarioT -> System.out.println(rolUsuarioT));
        
        List<ContactoDTO> contactos = contactoServicio.traerContactos();
        // Imprimís los clientes
        contactos.forEach(contactoT -> System.out.println(contactoT));
        
        List<LugarDTO> lugares = lugarServicio.traerLugares();
        // Imprimís los clientes
        lugares.forEach(lugarT -> System.out.println(lugarT));
        
        List<ServicioDTO> servicios = servicioServicio.traerServicios();
        // Imprimís los clientes
        servicios.forEach(servicioT -> System.out.println(servicioT));
        
        System.out.println("----- MODIFICAR------");
        
        ClienteDTO clientedto = new ClienteDTO();
        clientedto.setNombre("Juan");
        clientedto.setApellido("Pérez");
        clientedto.setDni("12345678");
        clientedto.setCuit("20-12345678-9");
        Optional<ClienteDTO> clienteModificado = clienteServicio.modificarCliente(2L, clientedto);
        
        EmpleadoDTO empleadodto = new EmpleadoDTO();
        empleadodto.setNombre("Ariel");
        empleadodto.setApellido("Ortega");
        empleadodto.setDni("9999999999");
        empleadodto.setFechaInicio(LocalDate.now());
        EmpleadoDTO empleadoModificado = empleadoServicio.modificarEmpleado(1L, empleadodto);
               
        UsuarioDTO usuariodto = new UsuarioDTO();
        usuariodto.setNombreUsuario("pepebiondi");
        usuariodto.setContraseniaUsuario("875421");
        usuariodto.setEstado(false);
        UsuarioDTO usuarioModificado = usuarioServicio.modificarUsuario(3L, usuariodto);
        
        RolUsuarioDTO rolusuariodto = new RolUsuarioDTO();
        rolusuariodto.setRole("CLIENTE");
        rolusuariodto.setFechaCreacion(LocalDateTime.now());
        RolUsuarioDTO rolUsuarioModificado = rolUsuarioServicio.modificarRolUsuario(6L, rolusuariodto);
        
        ContactoDTO contactodto = new ContactoDTO();
        contactodto.setDireccion("Av. siempre viva 742");
        contactodto.setEmail("nuevomaildecontacto@gmail.com");
        contactodto.setTelefono("5555555");
        ContactoDTO contactoModificado = contactoServicio.modificarContacto(11L, contactodto);
        
        LugarDTO lugardto = new LugarDTO();
        lugardto.setDireccion("Connecticut");
        LugarDTO lugarModificado = lugarServicio.modificarLugar(3L, lugardto);
        
        ServicioDTO serviciodto = new ServicioDTO();
        serviciodto.setFechaHoraFin(LocalDateTime.of(2025, 10, 10, 10, 0));
        serviciodto.setFechaHoraInicio(LocalDateTime.of(2025, 10, 10, 9, 0));
        serviciodto.setIdCliente(8L);
        serviciodto.setIdEmpleado(16L);
        serviciodto.setIdLugarServicio(8L);
        ServicioDTO servicioModificado = servicioServicio.modificarServicio(2L, serviciodto);
        
        
        System.out.println("----- ELIMINAR------");
        
        contactoServicio.eliminarContacto(6L);
        clienteServicio.eliminarCliente(6L);
        empleadoServicio.eliminarEmpleado(70L);
        rolUsuarioServicio.eliminarRolUsuario(3L);
        usuarioServicio.eliminarUsuario(12L);
        lugarServicio.eliminarLugar(14L);
        servicioServicio.eliminarServicio(3L);
        
        
    }
}

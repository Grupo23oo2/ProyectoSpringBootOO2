package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.example.modelo.Cliente;
import org.example.modelo.Contacto;
import org.example.modelo.Empleado;
import org.example.modelo.Lugar;
import org.example.modelo.RolUsuario;
import org.example.modelo.Servicio;
import org.example.modelo.Usuario;
import org.example.repositorios.IClienteRepositorio;
import org.example.repositorios.IContactoRepositorio;
import org.example.repositorios.IEmpleadoRepositorio;
import org.example.repositorios.ILugarRepositorio;
import org.example.repositorios.IRolUsuarioRepositorio;
import org.example.repositorios.IServicioRepositorio;
import org.example.repositorios.IUsuarioRepositorio;
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
        
        
        
        Empleado empleado = new Empleado("carla", "gonzalez", "1111111111", null, LocalDate.now(), new HashSet<>());
        empleado = empleadoRepositorio.save(empleado);
        
        Cliente cliente = new Cliente("carlos", "gutierrez", "2222222222",null, "4444444444", null, new HashSet<>()); 
        cliente = clienteRepositorio.save(cliente);
       
        Cliente cliente2 = new Cliente("ssss", "aaaa", "3333333333",null, "6666666666", null, new HashSet<>());
        Usuario usuario = new Usuario (cliente2, "peperino", "123456", true, new HashSet<>());
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
        
          
        
        
        
        
        
        
        
    }
}

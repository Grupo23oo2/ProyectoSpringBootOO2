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
import org.example.turnos.dtos.TurnoDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Contacto;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Lugar;
import org.example.turnos.modelo.RolUsuario;
import org.example.turnos.modelo.Turno;
import org.example.turnos.modelo.Usuario;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IContactoRepositorio;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.ILugarRepositorio;
import org.example.turnos.repositorios.IRolUsuarioRepositorio;
import org.example.turnos.repositorios.ITurnoRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IClienteServicio;
import org.example.turnos.servicios.IContactoServicio;
import org.example.turnos.servicios.IEmpleadoServicio;
import org.example.turnos.servicios.ILugarServicio;
import org.example.turnos.servicios.IRolUsuarioServicio;
import org.example.turnos.servicios.ITurnoServicio;
import org.example.turnos.servicios.IUsuarioServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProyectoSpringBootOO2Application {
    public static void main(String[] args) {
    //	SpringApplication.run(ProyectoSpringBootOO2Application.class, args);
    	
    	ApplicationContext context = SpringApplication.run(ProyectoSpringBootOO2Application.class, args); 
    }
}

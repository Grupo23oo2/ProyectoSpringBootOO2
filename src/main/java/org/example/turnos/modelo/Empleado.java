package org.example.turnos.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empleado")
@PrimaryKeyJoinColumn(name = "idPersona")
public class Empleado extends Persona {

    private LocalDate fechaInicio;

    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Servicio> servicios = new HashSet<>();
    
    public Empleado() {
    	
    }

    public Empleado(Long idPersona, String nombre, String apellido, String dni, Usuario usuario, LocalDate fechaInicio, Set<Servicio> servicios) {
        super(idPersona, nombre, apellido, dni, usuario);
        this.fechaInicio = fechaInicio;
        this.servicios = servicios;
    }

	public Empleado(String nombre, String apellido, String dni, Usuario usuario, LocalDate fechaInicio,
			Set<Servicio> servicios) {
		super(nombre, apellido, dni, usuario);
		this.fechaInicio = fechaInicio;
		this.servicios = servicios;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}
    
}

package org.example.turnos.modelo;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "idPersona")
public class Cliente extends Persona {

    private String cuit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn // le dice que comparten el mismo id
    private Contacto contacto;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Servicio> servicios = new HashSet<>();
    
    public Cliente() {
    	
    }

    public Cliente(Long idPersona, String nombre, String apellido, String dni, Usuario usuario, String cuit, Contacto contacto, Set<Servicio> servicios) {
        super(idPersona, nombre, apellido, dni, usuario);
        this.cuit = cuit;
        this.contacto = contacto;
        this.servicios = servicios;
    }

	public Cliente(String nombre, String apellido, String dni, Usuario usuario, String cuit, Contacto contacto,
			Set<Servicio> servicios) {
		super(nombre, apellido, dni, usuario);
		this.cuit = cuit;
		this.contacto = contacto;
		this.servicios = servicios;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}
	
	

    
    
}

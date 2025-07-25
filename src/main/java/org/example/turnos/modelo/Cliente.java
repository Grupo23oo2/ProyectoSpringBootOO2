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
    @PrimaryKeyJoinColumn // comparten el mismo id
    private Contacto contacto;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Turno> turnos = new HashSet<>();
    
    public Cliente() {
    	
    }

    public Cliente(Long idPersona, String nombre, String apellido, String dni, Usuario usuario, String cuit, Contacto contacto, Set<Turno> turnos) {
        super(idPersona, nombre, apellido, dni, usuario);
        this.cuit = cuit;
        this.contacto = contacto;
        this.turnos = turnos;
    }

	public Cliente(String nombre, String apellido, String dni, Usuario usuario, String cuit, Contacto contacto,
			Set<Turno> turnos) {
		super(nombre, apellido, dni, usuario);
		this.cuit = cuit;
		this.contacto = contacto;
		this.turnos = turnos;
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

	public Set<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(Set<Turno> turnos) {
		this.turnos = turnos;
	}
	
	

    
    
}

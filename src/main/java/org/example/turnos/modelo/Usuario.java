package org.example.turnos.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    private long idUsuario;

    @OneToOne
    @MapsId // Le dice a JPA que el ID de este Usuario viene de la entidad relacionada (Persona)
    @JoinColumn(name = "idUsuario") // FK que es también PK
    private Persona persona;
    
    @Column(name="email") @Email
    private String email;

    @Column(name="nombreUsuario", unique=true, nullable=false, length=45)
    private String nombreUsuario;

    @Column(name="contraseniaUsuario", nullable=false, length=60)
    private String contraseniaUsuario;

    private boolean estado;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolUsuario> rolesUsuario = new HashSet<>();

	public Usuario() {
		
	}
    
   
    
    public Usuario(long idUsuario, Persona persona, String email, String nombreUsuario, String contraseniaUsuario,
			boolean estado, Set<RolUsuario> rolesUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.persona = persona;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
		this.rolesUsuario = rolesUsuario;
	}



	public Usuario(Persona persona, String email, String nombreUsuario, String contraseniaUsuario, boolean estado) {
		super();
		this.persona = persona;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseniaUsuario() {
		return contraseniaUsuario;
	}

	public void setContraseniaUsuario(String contraseniaUsuario) {
		this.contraseniaUsuario = contraseniaUsuario;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Set<RolUsuario> getRolesUsuario() {
		return rolesUsuario;
	}

	public void setRolesUsuario(Set<RolUsuario> rolesUsuario) {
		this.rolesUsuario = rolesUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}

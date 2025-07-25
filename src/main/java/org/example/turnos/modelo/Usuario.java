package org.example.turnos.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;


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


    @Column(name = "rol", nullable = false, length=100)
    private String rol; // Ej: "ROLE_ADMIN", "ROLE_EMPLEADO", "ROLE_CLIENTE"

    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    
    public Usuario(long idUsuario, Persona persona, String email, String nombreUsuario, String contraseniaUsuario,
			boolean estado, String rol, LocalDateTime fechaCreacion) {
		super();
		this.idUsuario = idUsuario;
		this.persona = persona;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
		this.rol = rol;
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario(Persona persona, @Email String email, String nombreUsuario, String contraseniaUsuario,
			boolean estado, String rol, LocalDateTime fechaCreacion) {
		super();
		this.persona = persona;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
		this.rol = rol;
		this.fechaCreacion = fechaCreacion;
	}
    

	public Usuario() {
		
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}

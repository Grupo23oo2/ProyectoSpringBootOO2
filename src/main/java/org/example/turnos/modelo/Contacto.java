package org.example.turnos.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class Contacto {

    @Id
    private Long idContacto; // Es igual al idPersona del Cliente
    private String direccion;
    private String email; 
    private String telefono;
    
    public Contacto() {
    	
    }

    public Contacto(String direccion, String email, String telefono) {
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
   
}

package org.example.turnos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Contacto {

    @Id
    private Long idContacto; // Es igual al idPersona del Cliente
    private String direccion;
    @Column (unique = true)
    private String email; 
    private String telefono;
    
    @OneToOne
    @MapsId // el idContacto se asigna automáticamente desde cliente.idPersona
    @JoinColumn(name = "idContacto")
    private Cliente cliente;
    
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
	
	public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

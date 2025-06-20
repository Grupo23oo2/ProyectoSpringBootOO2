package org.example.turnos.modelo;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLugar;
    private String direccion;

    @OneToMany(mappedBy = "lugarTurno", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Turno> turnos = new HashSet<>();
    
    public Lugar() {
    	
    }

    public Lugar(String direccion) {
        this.direccion = direccion;
    }

	public Long getIdLugar() {
		return idLugar;
	}

	public void setIdLugar(Long idLugar) {
		this.idLugar = idLugar;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(Set<Turno> turnos) {
		this.turnos = turnos;
	}
    
}

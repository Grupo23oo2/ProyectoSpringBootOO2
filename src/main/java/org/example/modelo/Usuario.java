package org.example.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Usuario {

    @Id
    private long idUsuario;

    @OneToOne
    @MapsId // Le dice a JPA que el ID de este Usuario viene de la entidad relacionada (Persona)
    @JoinColumn(name = "idUsuario") // FK que es también PK
    private Persona persona;

    @Column(name="nombreUsuario", unique=true, nullable=false, length=45)
    private String nombreUsuario;

    @Column(name="contraseniaUsuario", nullable=false, length=60)
    private String contraseniaUsuario;

    private boolean estado;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolUsuario> rolesUsuario = new HashSet<>();

    public Usuario(long idUsuario, String nombreUsuario, String contraseniaUsuario, boolean estado, Set<RolUsuario> rolesUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.estado = estado;
        this.rolesUsuario = rolesUsuario;
    }

	public Usuario(Persona persona, String nombreUsuario, String contraseniaUsuario, boolean estado,
			Set<RolUsuario> rolesUsuario) {
		super();
		this.persona = persona;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
		this.rolesUsuario = rolesUsuario;
	}
    
    
    

}

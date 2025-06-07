package modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="RolUsuario", uniqueConstraints=@UniqueConstraint(columnNames= {"role", "id_Usuario"}))
public class RolUsuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long idRolUsuario;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id_Usuario", nullable=false)
        private Usuario usuario;

        @Column(name="role", nullable=false, length=100)
        private String role;

        @CreationTimestamp
        private LocalDateTime fechaCreacion;

    public RolUsuario(long idRolUsuario, Usuario usuario, String role, LocalDateTime fechaCreacion) {
        this.idRolUsuario = idRolUsuario;
        this.usuario = usuario;
        this.role = role;
        this.fechaCreacion = fechaCreacion;
    }

	public long getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(long idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


    

}


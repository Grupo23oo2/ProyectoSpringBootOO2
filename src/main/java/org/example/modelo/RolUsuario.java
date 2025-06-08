package org.example.modelo;

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

}


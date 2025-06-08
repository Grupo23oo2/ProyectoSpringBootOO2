package org.example.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    private boolean presencial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lugar_id", nullable = false)
    private Lugar lugarServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    public Servicio(boolean presencial, Lugar lugarServicio, Empleado empleado, Cliente cliente, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        this.presencial = presencial;
        this.lugarServicio = lugarServicio;
        this.empleado = empleado;
        this.cliente = cliente;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
    }

}

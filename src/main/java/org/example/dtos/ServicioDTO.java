package org.example.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ServicioDTO {

    @Getter @Setter
    private Long idServicio;

    @Getter @Setter
    private boolean presencial;

    @Getter @Setter
    private Long idLugarServicio;

    @Getter @Setter
    private Long idEmpleado;

    @Getter @Setter
    private Long idCliente;

    @Getter @Setter
    private LocalDateTime fechaHoraInicio;

    @Getter @Setter
    private LocalDateTime fechaHoraFin;

    public ServicioDTO() {
    }

    public ServicioDTO(Long idServicio, boolean presencial, Long idLugarServicio, Long idEmpleado, Long idCliente,
                       LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        this.idServicio = idServicio;
        this.presencial = presencial;
        this.idLugarServicio = idLugarServicio;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
    }

}


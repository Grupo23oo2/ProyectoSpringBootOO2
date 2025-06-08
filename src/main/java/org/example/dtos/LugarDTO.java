package org.example.dtos;

import lombok.Getter;
import lombok.Setter;

public class LugarDTO {

    @Getter @Setter
    private Long idLugar;

    @Getter @Setter
    private String direccion;

    public LugarDTO() {
    }

    public LugarDTO(Long idLugar, String direccion) {
        this.idLugar = idLugar;
        this.direccion = direccion;
    }

}
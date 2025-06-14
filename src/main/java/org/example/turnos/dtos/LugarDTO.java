package org.example.turnos.dtos;

public class LugarDTO {

    private Long idLugar;
    private String direccion;

    public LugarDTO() {
    }

    public LugarDTO(Long idLugar, String direccion) {
        this.idLugar = idLugar;
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
    
    

}
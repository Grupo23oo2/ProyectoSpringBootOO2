package dtos;

import lombok.Getter;
import lombok.Setter;

public class ContactoDTO {

    @Getter @Setter
    private Long idContacto;

    @Getter @Setter
    private String direccion;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String telefono;

    public ContactoDTO() {}

    public ContactoDTO(Long idContacto, String direccion, String email, String telefono) {
        this.idContacto = idContacto;
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

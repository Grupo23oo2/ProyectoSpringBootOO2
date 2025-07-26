package org.example.turnos.servicios;

import org.example.turnos.dtos.ContactoDTO;

import java.util.List;

public interface IContactoServicio {

    public ContactoDTO agregarContacto(ContactoDTO dto);

    public ContactoDTO traerContacto(String email);

    public List<ContactoDTO> traerContactos();

    public ContactoDTO modificarContacto(String email, ContactoDTO dto);

    public void eliminarContacto(String email);
}

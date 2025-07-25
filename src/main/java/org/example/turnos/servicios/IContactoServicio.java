package org.example.turnos.servicios;

import org.example.turnos.dtos.ContactoDTO;

import java.util.List;

public interface IContactoServicio {

    public ContactoDTO agregarContacto(ContactoDTO dto);

    public ContactoDTO traerContacto(Long id);

    public List<ContactoDTO> traerContactos();

    public ContactoDTO modificarContacto(Long id, ContactoDTO dto);

    public void eliminarContacto(Long id);
}

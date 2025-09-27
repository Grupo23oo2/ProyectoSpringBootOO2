package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Contacto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IContactoRepositorio;
import org.example.turnos.servicios.IContactoServicio;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactoServicio implements IContactoServicio {

    @Autowired
    private IContactoRepositorio contactoRepositorio;

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Override
    public ContactoDTO agregarContacto(ContactoDTO dto) {
        try {
            //Buscamos el cliente correspondiente
            var cliente = clienteRepositorio.findById(dto.idContacto())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("No existe un cliente con ID " + dto.idContacto()));

            //Verificamos si ya existe un contacto para ese cliente
            if (contactoRepositorio.existsById(dto.idContacto())) {
                throw new MiExcepcionPersonalizada("Ya existe un contacto para este cliente");
            }

            //Creamos el contacto y asignamos el cliente
            Contacto contacto = new Contacto();
            contacto.setDireccion(dto.direccion());
            contacto.setEmail(dto.email());
            contacto.setTelefono(dto.telefono());
            contacto.setCliente(cliente); //importante, asignamos el cliente

            //Guardamos el contacto; Hibernate asigna automáticamente el ID igual al idPersona del cliente
            Contacto guardado = contactoRepositorio.save(contacto);

            //Retornamos el DTO
            return new ContactoDTO(
                    guardado.getIdContacto(),
                    guardado.getDireccion(),
                    guardado.getEmail(),
                    guardado.getTelefono()
            );

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el Contacto: " + e.getMessage());
        }
    }

    @Override
    public ContactoDTO traerContacto(String email) {
        try {
            Contacto contacto = contactoRepositorio.findByEmail(email)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Contacto no encontrado: " + email));
            return new ContactoDTO(
                    contacto.getIdContacto(),
                    contacto.getDireccion(),
                    contacto.getEmail(),
                    contacto.getTelefono()
            );
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el contacto: " + e.getMessage());
        }
    }

    @Override
    public List<ContactoDTO> traerContactos() {
        try {
            return contactoRepositorio.findAll()
                    .stream()
                    .map(c -> new ContactoDTO(
                            c.getIdContacto(),
                            c.getDireccion(),
                            c.getEmail(),
                            c.getTelefono()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los contactos: " + e.getMessage());
        }
    }

    @Override
    public ContactoDTO modificarContacto(String email, ContactoDTO dto) {
        try {
            Contacto existente = contactoRepositorio.findByEmail(email)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("No existe un contacto con email: " + email));

            // Actualizamos solo los campos modificables
            existente.setDireccion(dto.direccion());
            existente.setEmail(dto.email());
            existente.setTelefono(dto.telefono());

            Contacto actualizado = contactoRepositorio.save(existente);

            return new ContactoDTO(
                    actualizado.getIdContacto(),
                    actualizado.getDireccion(),
                    actualizado.getEmail(),
                    actualizado.getTelefono()
            );
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo modificar el contacto: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void eliminarContacto(String email) {
        try {
            if (!contactoRepositorio.existsByEmail(email)) {
                throw new MiExcepcionPersonalizada("No existe un contacto con email: " + email);
            }
            contactoRepositorio.deleteByEmail(email);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo eliminar el contacto: " + e.getMessage());
        }
    }
}
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContactoDTO agregarContacto(ContactoDTO dto) {
    	try {
        if (!clienteRepositorio.existsById(dto.getIdContacto())) {
            throw new MiExcepcionPersonalizada("No existe un cliente con ID " + dto.getIdContacto());
        }

        if (contactoRepositorio.existsById(dto.getIdContacto())) {
            throw new MiExcepcionPersonalizada("Ya existe un contacto con ese ID");
        }

        Contacto contacto = modelMapper.map(dto, Contacto.class);
        contacto = contactoRepositorio.save(contacto);
        return modelMapper.map(contacto, ContactoDTO.class);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo agregar el Contacto" + e.getMessage());
        }
    }

    @Override
    public ContactoDTO traerContacto(String email) {
        try {
            return contactoRepositorio.findByEmail(email)
                    .map(contacto -> modelMapper.map(contacto, ContactoDTO.class))
                    .orElse(null);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el contacto: " + e.getMessage());
        }
    }

    @Override
    public List<ContactoDTO> traerContactos() {
    	try {
        return contactoRepositorio.findAll()
                .stream()
                .map(contacto -> modelMapper.map(contacto, ContactoDTO.class))
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los contactos" + e.getMessage());
        }
    }

    @Override
    public ContactoDTO modificarContacto(String email, ContactoDTO dto) {
        try {
            if (!contactoRepositorio.existsByEmail(email)) {
                throw new MiExcepcionPersonalizada("No existe un contacto con email: " + email);
            }

            Contacto existente = contactoRepositorio.findByEmail(email).get();
            dto.setIdContacto(existente.getIdContacto()); // mantener ID original
            Contacto contacto = modelMapper.map(dto, Contacto.class);
            contacto = contactoRepositorio.save(contacto);
            return modelMapper.map(contacto, ContactoDTO.class);
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
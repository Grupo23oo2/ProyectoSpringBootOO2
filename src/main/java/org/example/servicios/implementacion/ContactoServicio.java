package org.example.servicios.implementacion;

import org.example.dtos.ContactoDTO;
import org.example.excepciones.MiExcepcionPersonalizada;
import org.example.modelo.Contacto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repositorios.IClienteRepositorio;
import org.example.repositorios.IContactoRepositorio;
import org.example.servicios.IContactoServicio;

import java.util.List;
import java.util.NoSuchElementException;
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
    public ContactoDTO traerContacto(Long id) {
    	try {
        return contactoRepositorio.findById(id)
                .map(contacto -> modelMapper.map(contacto, ContactoDTO.class))
                .orElse(null);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo trer el contacto" + e.getMessage());
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
    public ContactoDTO modificarContacto(Long id, ContactoDTO dto) {
    	try {
        if (!contactoRepositorio.existsById(id)) {
            throw new MiExcepcionPersonalizada("No existe un contacto con ID " + id);
        }
        dto.setIdContacto(id);
        Contacto contacto = modelMapper.map(dto, Contacto.class);
        contacto = contactoRepositorio.save(contacto);
        return modelMapper.map(contacto, ContactoDTO.class);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar el contacto" + e.getMessage());
        }
    }

    @Override
    public void eliminarContacto(Long id) {
    	try {
        contactoRepositorio.deleteById(id);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el contacto" + e.getMessage());
        }
    }

}
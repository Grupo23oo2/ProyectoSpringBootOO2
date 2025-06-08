package org.example.servicios.implementacion;

import org.example.dtos.ContactoDTO;
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
        if (!clienteRepositorio.existsById(dto.getIdContacto())) {
            throw new IllegalArgumentException("No existe un cliente con ID " + dto.getIdContacto());
        }

        if (contactoRepositorio.existsById(dto.getIdContacto())) {
            throw new IllegalArgumentException("Ya existe un contacto con ese ID");
        }

        Contacto contacto = modelMapper.map(dto, Contacto.class);
        contacto = contactoRepositorio.save(contacto);
        return modelMapper.map(contacto, ContactoDTO.class);
    }

    @Override
    public ContactoDTO traerContacto(Long id) {
        return contactoRepositorio.findById(id)
                .map(contacto -> modelMapper.map(contacto, ContactoDTO.class))
                .orElse(null);
    }

    @Override
    public List<ContactoDTO> traerContactos() {
        return contactoRepositorio.findAll()
                .stream()
                .map(contacto -> modelMapper.map(contacto, ContactoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactoDTO modificarContacto(Long id, ContactoDTO dto) {
        if (!contactoRepositorio.existsById(id)) {
            throw new NoSuchElementException("No existe un contacto con ID " + id);
        }
        dto.setIdContacto(id);
        Contacto contacto = modelMapper.map(dto, Contacto.class);
        contacto = contactoRepositorio.save(contacto);
        return modelMapper.map(contacto, ContactoDTO.class);
    }

    @Override
    public void eliminarContacto(Long id) {
        contactoRepositorio.deleteById(id);
    }
}
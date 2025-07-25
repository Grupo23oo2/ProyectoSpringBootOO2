package org.example.turnos.configurations;

import org.example.turnos.modelo.Usuario;
import org.example.turnos.modelo.RolUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = usuario.getRolesUsuario().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getRole()))
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getContraseniaUsuario();
    }

    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return usuario.isEstado(); 
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
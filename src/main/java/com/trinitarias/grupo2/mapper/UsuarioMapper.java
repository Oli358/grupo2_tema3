package com.trinitarias.grupo2.mapper;

import org.springframework.stereotype.Component;

import com.trinitarias.grupo2.dto.UsuarioCreateDTO;
import com.trinitarias.grupo2.dto.UsuarioResponseDTO;
import com.trinitarias.grupo2.entity.Usuario;

/**
 * Convierte los datos entre los DTO y la entidad Usuario.
 */
@Component
public class UsuarioMapper {

    // Convierte el DTO de entrada en una entidad Usuario.
    public Usuario toEntity(UsuarioCreateDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        return usuario;
    }

    // Convierte una entidad Usuario en un DTO de respuesta.
    // No devuelve la contraseña por seguridad.
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }
}
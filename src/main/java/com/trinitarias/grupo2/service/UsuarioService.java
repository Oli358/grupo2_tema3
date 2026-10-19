package com.trinitarias.grupo2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trinitarias.grupo2.dto.UsuarioCreateDTO;
import com.trinitarias.grupo2.dto.UsuarioResponseDTO;
import com.trinitarias.grupo2.entity.Usuario;
import com.trinitarias.grupo2.mapper.UsuarioMapper;
import com.trinitarias.grupo2.repository.UsuarioRepository;

/**
 * Contiene la lógica de negocio relacionada con los usuarios.
 * Utiliza el Mapper para convertir entre DTO y Entity
 * y el Repository para acceder a la base de datos.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    // Constructor: Spring inyecta el Repository y el Mapper.
    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    // 1. Crear un usuario.
    public UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto) {

        // Convertimos el DTO de entrada en una entidad.
        Usuario usuario = usuarioMapper.toEntity(dto);

        // Guardamos la entidad en la base de datos.
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Devolvemos el DTO de salida, sin contraseña.
        return usuarioMapper.toResponseDTO(usuarioGuardado);
    }

    // 2. Buscar un usuario por su ID.
    public UsuarioResponseDTO buscarPorId(Long id) {

        // Buscamos el usuario en la base de datos.
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "No existe un usuario con ID: " + id
                    )
                );

        // Convertimos la entidad en DTO de respuesta.
        return usuarioMapper.toResponseDTO(usuario);
    }

    // 3. Listar todos los usuarios.
    public List<UsuarioResponseDTO> listarUsuarios() {

        // Obtenemos todas las entidades de la base de datos.
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Convertimos cada entidad en un DTO de respuesta.
        return usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }
}
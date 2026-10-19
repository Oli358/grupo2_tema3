package com.trinitarias.grupo2.controller;

import com.trinitarias.grupo2.entity.Usuario;
import com.trinitarias.grupo2.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Endpoint solo para la demo: devuelve entidades con la contraseña.
    @GetMapping("/inseguro")
    public List<Usuario> obtenerTodosMal() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}/inseguro")
    public Usuario obtenerPorIdMal(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}

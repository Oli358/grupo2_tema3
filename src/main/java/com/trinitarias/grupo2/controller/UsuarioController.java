package com.trinitarias.grupo2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    
    @GetMapping("/mal")
    public List<Usuario> obtenerTodosMal() {
        return usuarioRepository.findAll();
    }

   
    @GetMapping("/mal/{id}")
    public Usuario obtenerPorIdMal(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}


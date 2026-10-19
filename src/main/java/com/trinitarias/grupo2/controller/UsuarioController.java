package com.trinitarias.grupo2.controller;

import com.trinitarias.grupo2.dto.UsuarioCreateDTO;
import com.trinitarias.grupo2.dto.UsuarioResponseDTO;
import com.trinitarias.grupo2.entity.Usuario;
import com.trinitarias.grupo2.repository.UsuarioRepository;
import com.trinitarias.grupo2.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador de usuarios.
 *
 * Endpoints "correctos": solo reciben DTOs de entrada (UsuarioCreateDTO)
 * y devuelven DTOs de salida (UsuarioResponseDTO), nunca la entidad.
 * Flujo: Controller -> DTO -> Service -> Entity
 *
 * Endpoints "inseguros" (/inseguro): SOLO para la demo. Devuelven la entidad
 * Usuario directamente desde el repositorio, mostrando la contraseña.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    // Inyección por constructor (recomendada frente a @Autowired en el campo)
    public UsuarioController(UsuarioService usuarioService,
                             UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    // ===================== Endpoints correctos (DTOs) =====================

    /** GET /api/usuarios -> lista de usuarios SIN password */
    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listarUsuarios();
    }

    /** GET /api/usuarios/{id} -> un usuario SIN password (404 si no existe) */
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/usuarios -> crea un usuario.
     * Entra un DTO de entrada (con password), sale un DTO de salida (sin password).
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioCreateDTO request) {
        UsuarioResponseDTO creado = usuarioService.crearUsuario(request);
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + creado.getId()))
                .body(creado);
    }

    // ============== Endpoints inseguros (solo para la demo) ==============

    /** GET /api/usuarios/inseguro -> lista de entidades CON password (ejemplo malo) */
    @GetMapping("/inseguro")
    public List<Usuario> inseguro() {
        return usuarioRepository.findAll();
    }

    /** GET /api/usuarios/{id}/inseguro -> una entidad CON password (ejemplo malo) */
    @GetMapping("/{id:[0-9]+}/inseguro")
    public Usuario inseguroPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}

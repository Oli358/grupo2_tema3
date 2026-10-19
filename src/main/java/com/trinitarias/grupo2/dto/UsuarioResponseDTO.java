package com.trinitarias.grupo2.dto;

/**
 * DTO de salida: representa los datos de un usuario que sí pueden
 * exponerse al cliente. La contraseña se omite intencionadamente.
 */
public class UsuarioResponseDTO {

    private final Long id;
    private final String nombre;
    private final String email;

    public UsuarioResponseDTO(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}

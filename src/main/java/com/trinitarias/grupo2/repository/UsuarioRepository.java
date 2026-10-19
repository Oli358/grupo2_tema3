package com.trinitarias.grupo2.repository;

import com.trinitarias.grupo2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

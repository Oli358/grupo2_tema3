package com.trinitarias.grupo2;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
	public class DataInitializer implements CommandLineRunner {

	    private final UsuarioRepository usuarioRepository;

	    public DataInitializer(UsuarioRepository usuarioRepository) {
	        this.usuarioRepository = usuarioRepository;
	    }

	    @Override
	    public void run(String... args) {
	        usuarioRepository.save(new Usuario("Jesus", "jesus@correo.com", "claveSuperSecreta123"));
	        usuarioRepository.save(new Usuario("Oliver", "oliver@correo.com", "adminPassword999"));
	    }
	}


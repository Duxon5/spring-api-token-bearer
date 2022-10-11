package com.kazale.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.kazale.api.security.entities.Usuario;
import com.kazale.api.security.enums.PerfilEnum;
import com.kazale.api.security.repositories.UsuarioRepository;
import com.kazale.api.services.ExemploCacheServices;
import com.kazale.api.utils.SenhaUtils;

@SpringBootApplication
@EnableCaching
public class P27Application {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ExemploCacheServices exemploCacheService;
	
	public static void main(String[] args) {
		SpringApplication.run(P27Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			Usuario usuario = new Usuario();
			usuario.setEmail("usuario@email.com");
			usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
			usuario.setSenha(SenhaUtils.gerarBCrypt("123456"));
			this.usuarioRepository.save(usuario);
			
			Usuario admin = new Usuario();
			admin.setEmail("admin@email.com");
			admin.setPerfil(PerfilEnum.ROLE_ADMIN);
			admin.setSenha(SenhaUtils.gerarBCrypt("123456"));
			this.usuarioRepository.save(admin);
			
			ehcache();
			
		};
	}
	
	private void ehcache() {
		System.out.println("Executando serviço pela primeira vez: ");
		System.out.println(this.exemploCacheService.exemploCache());
		
		System.out.println("Executando serviço pela segunda vez, deve obter dados do cache: ");
		System.out.println(this.exemploCacheService.exemploCache());
	}
}

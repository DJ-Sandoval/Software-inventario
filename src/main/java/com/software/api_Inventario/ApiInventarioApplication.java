package com.software.api_Inventario;

import com.software.api_Inventario.persistence.entities.PermissionEntity;
import com.software.api_Inventario.persistence.entities.RoleEntity;
import com.software.api_Inventario.persistence.entities.UserEntity;
import com.software.api_Inventario.persistence.enums.RoleEnum;
import com.software.api_Inventario.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ApiInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiInventarioApplication.class, args);
	}
}

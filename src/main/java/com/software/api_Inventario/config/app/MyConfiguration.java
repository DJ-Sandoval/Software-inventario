package com.software.api_Inventario.config.app;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiguration {
    @Bean
    public WebMvcConfigurer globalCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // Permite todas las solicitudes de cualquier dominio
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATH")
                        .allowedHeaders("*"); // Permite todos los headers
            }
        };
    }
}

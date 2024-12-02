package org.example.backmobile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Spécifie les routes exactes si nécessaire
        registry.addMapping("/emargements/**")
                .allowedOrigins("http://localhost:3000") // Ou l'origine que tu souhaites autoriser
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Ajoute ou retire des méthodes selon tes besoins
                .allowedHeaders("")
                .allowCredentials(false);
    }
}


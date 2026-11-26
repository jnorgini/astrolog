package com.norgini.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI astrologOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Astrolog API")
                        .description("API para cálculo de mapa astral e trânsitos via Swiss Ephemeris, incluindo posições planetárias, casas (Placidus), Ascendente e fases da Lua. Também oferece autocompletar de localidades via Photon.")
                        .version("1.0.0")
                        .contact(new Contact().name("Norgini")));
    }
    
}

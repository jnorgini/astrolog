package com.norgini.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.norgini.application.service.AstrologApplicationService;
import com.norgini.domain.port.out.AstrologyCalculatorPort;
import com.norgini.domain.port.out.GeocodingPort;

@Configuration
public class BeanConfiguration {

    @Bean
    public AstrologApplicationService astrologApplicationService(
            AstrologyCalculatorPort astrologyCalculatorPort, GeocodingPort geocodingPort) {
        return new AstrologApplicationService(astrologyCalculatorPort, geocodingPort);
    }
    
}

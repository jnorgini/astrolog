package com.norgini.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;
import com.norgini.domain.model.PlanetPosition;
import com.norgini.infrastructure.adapter.in.web.dto.PlanetPositionResponse;

@Component
public class AstrologWebMapper {

    public PlanetPositionResponse toResponse(PlanetPosition position) {
        if (position.planet() != null) {
            String movement = position.isRetrograde() ? "retrógrado" : "direto";
            return new PlanetPositionResponse(
                    position.planet().getDisplayName(),
                    position.zodiacSign().getDisplayName(),
                    position.degrees(),
                    String.valueOf(position.house()),
                    movement,
                    position.moonPhase());
        }

        return new PlanetPositionResponse(
                position.moonPhase(),
                position.zodiacSign().getDisplayName(),
                position.degrees(),
                "", "", null);
    }
    
}

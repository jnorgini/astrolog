package com.norgini.domain.port.out;

import com.norgini.domain.model.Coordinates;
import com.norgini.domain.model.PlanetPosition;
import java.util.List;

public interface AstrologyCalculatorPort {
	
    List<PlanetPosition> calculatePositions(
            int day, 
            int month, 
            int year, 
            int hour, 
            int minute, 
            Coordinates coordinates);
    
}

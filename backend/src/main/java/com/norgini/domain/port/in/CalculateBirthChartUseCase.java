package com.norgini.domain.port.in;

import java.util.List;
import com.norgini.domain.model.PlanetPosition;

public interface CalculateBirthChartUseCase {
	
    List<PlanetPosition> calculatePositions(
    		int day, 
    		int month, 
    		int year, 
    		int hour, 
    		int minute, 
    		String location);
    
}

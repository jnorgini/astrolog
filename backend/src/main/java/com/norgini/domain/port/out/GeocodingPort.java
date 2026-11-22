package com.norgini.domain.port.out;

import java.util.List;
import com.norgini.domain.model.Coordinates;

public interface GeocodingPort {
	
    Coordinates findCoordinates(String location);
    List<String> searchLocations(String query);
    
}

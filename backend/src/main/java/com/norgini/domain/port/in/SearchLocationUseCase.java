package com.norgini.domain.port.in;

import java.util.List;

public interface SearchLocationUseCase {
	
    List<String> getLocationsSuggestions(String query);
    
}

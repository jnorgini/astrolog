package com.norgini.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AstrologQueryParams(
		@Min(value = 1, message = "O dia deve ser entre 1 e 31.") 
		@Max(value = 31, message = "O dia deve ser entre 1 e 31.") int day,

		@Min(value = 1, message = "O mês deve ser entre 1 e 12.") 
		@Max(value = 12, message = "O mês deve ser entre 1 e 12.") int month,

		@Min(value = -5000, message = "O ano está fora dos limites suportados.") 
		@Max(value = 5000, message = "O ano está fora dos limites suportados.") int year,

		@Min(value = 0, message = "A hora deve ser entre 0 e 23.") 
		@Max(value = 23, message = "A hora deve ser entre 0 e 23.") int hour,

		@Min(value = 0, message = "O minuto deve ser entre 0 e 59.") 
		@Max(value = 59, message = "O minuto deve ser entre 0 e 59.") int minute,

		@NotBlank(message = "A localização é obrigatória.") String location) {
}

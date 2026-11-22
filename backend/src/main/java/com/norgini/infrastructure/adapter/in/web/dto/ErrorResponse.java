package com.norgini.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String error, String message, int status, LocalDateTime timestamp) {}

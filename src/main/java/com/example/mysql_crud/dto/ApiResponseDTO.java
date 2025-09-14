package com.example.mysql_crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Generic API response wrapper with timestamp")
public class ApiResponseDTO<T> {

    @Schema(description = "HTTP status code of the response", example = "200")
    private int status;

    @Schema(description = "Message describing the result of the operation", example = "User created successfully")
    private String message;

    @Schema(description = "Timestamp when the response was generated", example = "2025-09-14T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Payload of the response. Can be a single object, list, or null")
    private T data;

    public ApiResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }
}

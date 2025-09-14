package com.example.mysql_crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details about an exception/error response")
public class ExceptionResponseDTO {

    @Schema(description = "HTTP status of the response", example = "404")
    private HttpStatus status;

    @Schema(description = "Error type or reason", example = "Not Found")
    private String error;

    @Schema(description = "Detailed message explaining the error", example = "User with ID 10 not found")
    private String message;

    @Schema(description = "The endpoint path that caused the error", example = "/users/10")
    private String path;

    @Schema(description = "Timestamp when the error occurred", example = "2025-09-14T10:30:00")
    private LocalDateTime timestamp;
}

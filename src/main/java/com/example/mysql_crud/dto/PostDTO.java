package com.example.mysql_crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Post")
public class PostDTO {

    @Schema(description = "Unique identifier of the post", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Title of the post", example = "My First Post")
    private String title;

    @Schema(description = "Content of the post", example = "This is the body of my first post")
    private String content;

    @Schema(description = "Timestamp when the post was created", example = "2025-09-14T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "ID of the user who created the post", example = "1")
    private Long userId;
}

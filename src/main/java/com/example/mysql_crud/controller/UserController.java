package com.example.mysql_crud.controller;

import com.example.mysql_crud.dto.ApiResponseDTO;
import com.example.mysql_crud.dto.UserDTO;
import com.example.mysql_crud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.mysql_crud.constants.ErrorConstants.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = service.getAllUsers();

        ApiResponseDTO<List<UserDTO>> response =
                new ApiResponseDTO<>(HttpStatus.OK.value(), FETCHED_ALL_USERS, users);

        return ResponseEntity.ok()
                       .header("X-Total-Count", String.valueOf(users.size()))
                       .header("X-App-Version", "1.0.0")
                       .body(response);
    }

    @Operation(summary = "Get a user by ID", description = "Retrieve a user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUser(@PathVariable Long id) {
        UserDTO user = service.getUserById(id);

        ApiResponseDTO<UserDTO> response =
                new ApiResponseDTO<>(HttpStatus.OK.value(), FETCHED_USER_SUCCESSFULLY, user);

        return ResponseEntity.ok()
                       .header("X-Request-ID", UUID.randomUUID().toString())
                       .body(response);
    }

    @Operation(summary = "Create a new user", description = "Create a new user with name, email, and age")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(@RequestBody @Valid UserDTO dto) {
        UserDTO created = service.createUser(dto);

        ApiResponseDTO<UserDTO> response =
                new ApiResponseDTO<>(HttpStatus.CREATED.value(), USER_CREATED_SUCCESSFULLY, created);

        return ResponseEntity.status(HttpStatus.CREATED)
                       .header("Location", "/users/" + created.getId())
                       .header("X-Operation", "CREATE")
                       .body(response);
    }

    @Operation(summary = "Update a user", description = "Update an existing user's information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUser(
            @PathVariable Long id, @RequestBody @Valid UserDTO dto) {

        UserDTO updated = service.updateUser(id, dto);

        ApiResponseDTO<UserDTO> response =
                new ApiResponseDTO<>(HttpStatus.OK.value(), USER_UPDATED_SUCCESSFULLY, updated);

        return ResponseEntity.ok()
                       .header("X-Operation", "UPDATE")
                       .body(response);
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);

        ApiResponseDTO<Void> response =
                new ApiResponseDTO<>(HttpStatus.OK.value(), USER_DELETED_SUCCESSFULLY, null);

        return ResponseEntity.ok()
                       .header("X-Operation", "DELETE")
                       .body(response);
    }
}

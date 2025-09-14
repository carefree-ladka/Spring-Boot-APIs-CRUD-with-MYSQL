package com.example.mysql_crud.controller;

import com.example.mysql_crud.dto.PostDTO;
import com.example.mysql_crud.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}")
    @Operation(summary = "Create a post", description = "Only USER or ADMIN can create a post for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden: user cannot create post for another user",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@PathVariable Long userId,
                                              @RequestBody PostDTO postDTO,
                                              Authentication auth) {
        PostDTO created = postService.createPost(userId, postDTO, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get posts by user", description = "Retrieve all posts created by a specific user. Public endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        List<PostDTO> posts = postService.findPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get a single post", description = "Retrieve a single post by its ID. Public endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        PostDTO post = postService.findById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "Update a post", description = "Update a post by its ID. Only the post owner or ADMIN can update.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden: only owner or ADMIN can update",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#postId, authentication)")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId,
                                              @RequestBody PostDTO postDTO) {
        PostDTO updated = postService.updatePost(postId, postDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Delete a post", description = "Delete a post by its ID. Only the post owner or ADMIN can delete.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden: only owner or ADMIN can delete"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#postId, authentication)")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}

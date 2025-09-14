package com.example.mysql_crud.service;

import com.example.mysql_crud.dto.PostDTO;
import com.example.mysql_crud.entity.Post;
import com.example.mysql_crud.entity.User;
import com.example.mysql_crud.mapper.PostMapper;
import com.example.mysql_crud.repository.PostRepository;
import com.example.mysql_crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMapper postMapper;

    // Create post: USER or ADMIN
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PostDTO createPost(Long userId, PostDTO dto, Authentication auth) {
        User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

        // Users can only create for themselves
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
                    && !auth.getName().equals(user.getUsername())) {
            throw new RuntimeException("User cannot create post for another user");
        }

        Post post = postMapper.toEntity(dto);
        post.setUser(user);
        return postMapper.toDto(postRepository.save(post));
    }

    // Get posts by user: public
    public List<PostDTO> findPostsByUserId(Long userId) {
        return postMapper.toDtoList(postRepository.findByUserId(userId));
    }

    // Update post: only owner or ADMIN
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#postId, authentication)")
    public PostDTO updatePost(Long postId, PostDTO dto) {
        Post post = postRepository.findById(postId)
                            .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return postMapper.toDto(postRepository.save(post));
    }

    // Delete post: only owner or ADMIN
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#postId, authentication)")
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // Get single post: public
    public PostDTO findById(Long postId) {
        Post post = postRepository.findById(postId)
                            .orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.toDto(post);
    }
}

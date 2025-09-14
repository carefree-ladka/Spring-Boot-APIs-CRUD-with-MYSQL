package com.example.mysql_crud.config;


import com.example.mysql_crud.entity.Post;
import com.example.mysql_crud.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("postSecurity")
public class PostSecurity {

    private final PostRepository postRepository;

    public PostSecurity(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean isOwner(Long postId, Authentication authentication) {
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) return false;

        return post.getUser().getUsername().equals(authentication.getName());
    }
}

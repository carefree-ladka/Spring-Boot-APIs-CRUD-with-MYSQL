package com.example.mysql_crud.config;

import com.example.mysql_crud.entity.Post;
import com.example.mysql_crud.entity.User;
import com.example.mysql_crud.repository.PostRepository;
import com.example.mysql_crud.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            // Admin user
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // BCrypt encode
            admin.setRole("ADMIN");
            admin.setAge(35);

            // Normal users
            User user1 = new User();
            user1.setName("John Doe");
            user1.setEmail("john@example.com");
            user1.setUsername("john");
            user1.setPassword(passwordEncoder.encode("john123"));
            user1.setRole("USER");
            user1.setAge(30);

            User user2 = new User();
            user2.setName("Jane Smith");
            user2.setEmail("jane@example.com");
            user2.setUsername("jane");
            user2.setPassword(passwordEncoder.encode("jane123"));
            user2.setRole("USER");
            user2.setAge(25);

            // Save users
            userRepository.saveAll(List.of(admin, user1, user2));

            // Create posts for users
            Post post1 = new Post();
            post1.setTitle("John's First Post");
            post1.setContent("Hello, this is John's first post!");
            post1.setUser(user1);

            Post post2 = new Post();
            post2.setTitle("John's Second Post");
            post2.setContent("Another post from John.");
            post2.setUser(user1);

            Post post3 = new Post();
            post3.setTitle("Jane's First Post");
            post3.setContent("Hi, this is Jane's first post.");
            post3.setUser(user2);

            postRepository.saveAll(List.of(post1, post2, post3));

            System.out.println("✅ Sample users and posts loaded with roles.");
        }
    }
}

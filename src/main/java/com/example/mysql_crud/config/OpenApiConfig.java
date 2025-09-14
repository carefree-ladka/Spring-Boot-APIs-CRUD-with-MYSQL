package com.example.mysql_crud.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                       .group("MySQL CRUD API")
                       .pathsToMatch("/users/**", "/posts/**")
                       .build();
    }

    @Bean
    public io.swagger.v3.oas.models.OpenAPI springShopOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                       .info(new Info().title("MySQL CRUD API")
                                     .description("Spring Boot CRUD API with Users & Posts")
                                     .version("v1.0.0")
                                     .contact(new Contact().name("Pawan").email("pawan@example.com"))
                                     .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                       .externalDocs(new ExternalDocumentation()
                                             .description("Project Repository")
                                             .url("https://github.com/your-repo"));
    }
}

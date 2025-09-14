package com.example.mysql_crud.mapper;

import com.example.mysql_crud.dto.PostDTO;
import com.example.mysql_crud.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.id", target = "userId")
    PostDTO toDto(Post post);

    @Mapping(source = "userId",target = "user.id")
    Post toEntity(PostDTO dto);

    List<PostDTO> toDtoList(List<Post> posts);

    List<Post> toEntityList(List<PostDTO> dtoList);
}

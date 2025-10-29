package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.PostDto;
import org.example.blogmanagement.Models.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(Post post);
    List<PostDto> getAllPosts();
    PostDto getPostById(String id);
    PostDto updatePost(String id, Post postDetails);
    void deletePost(String id);
}

package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.PostDto;
import org.example.blogmanagement.Models.Post;
import org.example.blogmanagement.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(String id, Post postDetails) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        existingPost.setTitle(postDetails.getTitle());
        existingPost.setContent(postDetails.getContent());
        existingPost.setAuthor(postDetails.getAuthor());

        Post updatedPost = postRepository.save(existingPost);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    private PostDto mapToDto(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent(),post.getAuthor());
    }
}

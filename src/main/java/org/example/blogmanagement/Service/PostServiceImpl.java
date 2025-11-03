package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.PostDto;
import org.example.blogmanagement.GlobalExceptionHandling.resourcesExistsException;
import org.example.blogmanagement.Models.Post;
import org.example.blogmanagement.Repository.PostRepository;
import org.springframework.data.domain.Sort;
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
    public Post createPost(PostDto postDto) {
        if (postRepository.existsByAuthor(postDto.getAuthor())) {
            throw new resourcesExistsException("Post already exists: " + postDto.getAuthor());
        }
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        postRepository.save(post);

        return post;
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

    //  Sorting method (no @GetMapping here)
    @Override
    public List<PostDto> getAllPostsSorted(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        return postRepository.findAll(sort)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post) {
        return new PostDto( post.getTitle(), post.getContent(), post.getAuthor());
    }
}

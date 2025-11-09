package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.PostDto;
import org.example.blogmanagement.GlobalExceptionHandling.resourcesExistsException;
import org.example.blogmanagement.GlobalExceptionHandling.resourcesNotFoundException;
import org.example.blogmanagement.Models.Post;
import org.example.blogmanagement.Models.User;
import org.example.blogmanagement.Repository.PostRepository;
import org.example.blogmanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(PostDto postDto) {

     User user = userRepository.findById(postDto.getUser_id()).orElseThrow(() -> new resourcesNotFoundException("User was not found"));
        if (postRepository.existsByAuthor(postDto.getAuthor())) {
            throw new resourcesExistsException("Post already exists: " + postDto.getAuthor());
        }
        Post post = new Post();
        post.setUser_id(user.getUser_id());
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
        return new PostDto(post.getUser_id(), post.getTitle(), post.getContent(), post.getAuthor());
    }
}

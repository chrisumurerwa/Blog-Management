//package org.example.blogmanagement.Controller;
//
//import org.example.blogmanagement.Models.Post;
//import org.example.blogmanagement.Service.PostService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/posts")
//public class PostController {
//    private final PostService postService;
//
//    public PostController(PostService postService) {
//        this.postService = postService;
//    }
//
//    @PostMapping
//    public ResponseEntity<Post> create(@RequestBody Post post) {
//        return ResponseEntity.ok(postService.createPost(post));
//    }
//
//    @GetMapping
//    public ResponseEntity<  List<Post>> getAll() {
//        return ResponseEntity.ok(postService.getAllPosts());
//    }
//}

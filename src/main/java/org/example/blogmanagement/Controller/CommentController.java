package org.example.blogmanagement.Controller;

import org.example.blogmanagement.Dto.CommentDto;
import org.example.blogmanagement.Models.Comment;
import org.example.blogmanagement.Service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Comment")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment) {
        CommentDto createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComment());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable String id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable String id, @RequestBody Comment Comment) {
        return ResponseEntity.ok(commentService.updateComment(id, Comment));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }




}

package org.example.blogmanagement.Service;
import org.example.blogmanagement.Dto.CommentDto;
import org.example.blogmanagement.Models.Comment;
import org.example.blogmanagement.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }



    @Override
    public List<CommentDto> getAllComment() {
        return commentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(String id, Comment commentDetails) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        existingComment.setPostId(commentDetails.getPostId());
        existingComment.setText(commentDetails.getText());
        existingComment.setAuthor(commentDetails.getAuthor());

        Comment updatedComment = commentRepository.save(existingComment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }

    private CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getPostId(),
                comment.getText(),
                comment.getAuthor()
        );
    }
}

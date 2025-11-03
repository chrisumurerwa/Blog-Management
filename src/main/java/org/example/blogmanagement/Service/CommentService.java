package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.CommentDto;
import org.example.blogmanagement.Models.Comment;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto comment);
    List<CommentDto> getAllComment();
    CommentDto getCommentById(String id);
    CommentDto updateComment(String id, Comment comment);
    void deleteComment(String id);


}

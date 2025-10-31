package org.example.blogmanagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDto {
    private String Id;
    private String postId;
    private String text;
    private String author;


}

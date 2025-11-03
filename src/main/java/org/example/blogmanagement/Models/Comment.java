package org.example.blogmanagement.Models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document (collection = "Comment")
@NoArgsConstructor
@AllArgsConstructor

public class Comment {
    @Id

    private String id;
    private String postId;
    private String text;
    private String author;
}

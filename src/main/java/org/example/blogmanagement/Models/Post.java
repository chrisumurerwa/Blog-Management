
package org.example.blogmanagement.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Post")
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;



}

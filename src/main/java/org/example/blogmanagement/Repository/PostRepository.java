package org.example.blogmanagement.Repository;

import org.example.blogmanagement.Models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
     boolean existsByAuthor(String author) ;




}

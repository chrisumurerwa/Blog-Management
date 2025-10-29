package org.example.blogmanagement.Repository;

import org.example.blogmanagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

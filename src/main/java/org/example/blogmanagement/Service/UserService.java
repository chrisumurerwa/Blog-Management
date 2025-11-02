
package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.UserDto;
import org.example.blogmanagement.Models.User;

import java.util.List;

public interface UserService {

    UserDto createUser(User user);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);
    UserDto updateUser(Long id, User userDetails);

    void deleteUser(Long id);
}

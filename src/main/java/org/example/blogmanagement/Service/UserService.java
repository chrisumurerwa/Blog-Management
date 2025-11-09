
package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.UserDto;
import org.example.blogmanagement.Models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {

    UserDto createUser(User user);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long User_id);
    UserDto updateUser(Long User_id, User userDetails);

    void deleteUser(Long User_id);
    Page<UserDto> getAllUsersPaginated(int pageNo, int pageSize, String sortBy, String sortDir);
}

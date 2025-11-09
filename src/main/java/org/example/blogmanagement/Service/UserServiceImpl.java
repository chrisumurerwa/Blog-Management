package org.example.blogmanagement.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.blogmanagement.Dto.UserDto;
import org.example.blogmanagement.GlobalExceptionHandling.resourcesExistsException;
import org.example.blogmanagement.Models.User;
import org.example.blogmanagement.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j   //  Lombok annotation for logging
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  Create user and return DTO without password
    @Override
    public UserDto createUser(User user) {
        log.info("Attempting to create user: {}", user.getUsername()); //  Log info message

        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("User creation failed - username already exists: {}", user.getUsername());
            throw new resourcesExistsException("Username already exists: " + user.getUsername());
        }

        User savedUser = userRepository.save(user);
        log.debug("User saved successfully with ID: {}", savedUser.getUser_id());
        return mapToDto(savedUser);
    }

    //  Get all users
    @Override
    public List<UserDto> getAllUsers() {
        log.info("Fetching all users from database");
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("No users found in database");
        } else {
            log.debug("Total users found: {}", users.size());
        }

        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //  Get one user
    @Override
    public UserDto getUserById(Long User_id) {
        log.info("Fetching user with ID: {}", User_id);

        User user = userRepository.findById(User_id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", User_id);
                    return new RuntimeException("User not found with id: " + User_id);
                });

        log.debug("User retrieved successfully: {}", user.getUsername());
        return mapToDto(user);
    }

    //  Update user
    @Override
    public UserDto updateUser(Long User_id, User userDetails) {
        log.info("Updating user with ID: {}", User_id);

        User existingUser = userRepository.findById(User_id)
                .orElseThrow(() -> {
                    log.error("Update failed - user not found with ID: {}", User_id);
                    return new RuntimeException("User not found with id: " + User_id);
                });

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            log.debug("Password provided for update, updating password for user ID: {}", User_id);
            existingUser.setPassword(userDetails.getPassword());
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("User with ID {} updated successfully", User_id);

        return mapToDto(updatedUser);
    }

    //  Delete
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("Delete failed - user with ID {} not found", id);
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        log.info("User with ID {} deleted successfully", id);
    }

    // Pagination and Sorting
    @Override
    public Page<UserDto> getAllUsersPaginated(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Fetching paginated users: pageNo={}, pageSize={}, sortBy={}, sortDir={}",
                pageNo, pageSize, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);

        log.debug("Fetched {} users in current page", userPage.getNumberOfElements());

        return userPage.map(this::mapToDto);
    }

    // Helper method to hide password
    private UserDto mapToDto(User user) {
        return new UserDto(user.getUser_id(), user.getUsername(), user.getEmail());
    }
}

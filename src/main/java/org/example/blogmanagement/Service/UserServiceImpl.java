package org.example.blogmanagement.Service;

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

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  Create user and return DTO without password
    @Override
    public UserDto createUser(User user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new resourcesExistsException("Username already exists: " + user.getUsername());
        }
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    //  Get all users
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //  Get one user
    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToDto(user);
    }

    //  Update user
    @Override
    public UserDto updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update allowed fields
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        // Optional: only update password if provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(userDetails.getPassword());
        }

        // Save updated user
        User updatedUser = userRepository.save(existingUser);

        // Return DTO without password
        return mapToDto(updatedUser);
    }

    //  Delete
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Pagination and Sorting
    @Override
    public Page<UserDto> getAllUsersPaginated(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(this::mapToDto);
    }

    // Helper method to hide password
    private UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}

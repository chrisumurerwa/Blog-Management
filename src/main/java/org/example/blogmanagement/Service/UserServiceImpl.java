package org.example.blogmanagement.Service;

import org.example.blogmanagement.Dto.UserDto;
import org.example.blogmanagement.Models.User;
import org.example.blogmanagement.Repository.UserRepository;
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

    //  Delete
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Helper method to hide password
    private UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}

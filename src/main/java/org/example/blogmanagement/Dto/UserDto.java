package org.example.blogmanagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;          // used for responses or updating
    private String username;  // user's display name
    private String email;     // user's email


}

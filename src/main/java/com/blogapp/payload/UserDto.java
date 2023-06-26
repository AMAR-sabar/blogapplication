package com.blogapp.payload;


import com.blogapp.entities.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4 , max = 15, message = "User name should be in between 4 to 15 chars !!!")
    private String name;
    @NotEmpty
    @Email(message = "Invalid email format !!!")
    private String email;
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password should be in between 8-12 chars !!!")
    private String password;
    @NotEmpty
    @Size(max = 100, message = "only 100 chars !!!")
    private String about;
    private Set<Role> roles = new HashSet<>();
}

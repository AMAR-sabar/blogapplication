package com.blogapp.services.impl;

import com.blogapp.config.ConstantValue;
import com.blogapp.entities.Role;
import com.blogapp.entities.User;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.payload.UserDto;
import com.blogapp.repository.RoleRepo;
import com.blogapp.repository.UserRepo;
import com.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;


    //Register New User
    @Override
    public UserDto registerNewUser(UserDto userDto) {
      User user=  this.modelMapper.map(userDto,User.class);
      user.setPassword(this.passwordEncoder.encode(user.getPassword()));
      //roles
      Role role =  this.roleRepo.findById(ConstantValue.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser( userDto);
        User savedUser = userRepo.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User","Id",userId)));
        User update=dtoToUser(userDto);
        update.setId(userId);
        User  updated= userRepo.save(update);
        return userToDto(updated);

    }

    @Override
    public UserDto getUserById(Integer userId)
    {
        User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User","Id",userId)));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers()
    {
       List <User> user = userRepo.findAll();
       List<UserDto> userDto = user.stream().map(l-> userToDto(l)).collect(Collectors.toList());
        return userDto;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User","Id",userId)));
        userRepo.deleteById(userId);
    }

    private User dtoToUser(UserDto userDto)
    {
        User user = this.modelMapper.map(userDto, User.class);
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }
    private UserDto userToDto(User user)
    {
        UserDto userDto = modelMapper.map(user, UserDto.class);
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}

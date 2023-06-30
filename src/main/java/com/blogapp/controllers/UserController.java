package com.blogapp.controllers;

import com.blogapp.entities.EmailDetails;
import com.blogapp.payload.UserDto;
import com.blogapp.services.EmailService;
import com.blogapp.services.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.HandlerChain;
import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Controller
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    EmailService emailService;
    @Autowired
    EmailDetails emailDetails;

    //CREATE user
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userRegistered = userService.registerNewUser(userDto);
       // emailService.sendSimpleMail(userDto.getEmail(),emailDetails.getSubject(),emailDetails.getMsgBody());
        EmailDetails emailDetails = EmailDetails.createRegistrationEmail(userDto.getEmail());
        emailService.sendSimpleMail(userDto.getEmail(), emailDetails);
        return new ResponseEntity<>(userRegistered, HttpStatus.CREATED);
    }

//    //CREATE user
//    @PostMapping("/")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
//    {
//        UserDto createdUser = userService.createUser(userDto);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

    //PUT Update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
    {
         UserDto updatedUser = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }


    //DELETE Deleting
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId)
    {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User Deleted successfully",HttpStatus.OK);
    }
    //GET Getting user
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        //List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userService.getAllUsers());
    }
    //GET Getting user by ID
    @GetMapping("/{userId}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable Integer userId)
    {
        UserDto getUserById = userService.getUserById(userId);
        return new  ResponseEntity<>(getUserById,HttpStatus.OK);
    }
}

package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ChangePasswordRequest;
import com.example.apartmentmanagement.dto.request.UserCreationRequest;
import com.example.apartmentmanagement.dto.response.UserCreationResponse;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    UserService userService;
    @Operation(summary = "Get all users", description = "Show all users's details by privilege of Admin")
    @GetMapping("/all")
    public APIResponse<List<User>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return APIResponse.<List<User>>builder()
                .result(userService.getAllUsers())
                .build();
    }
    @Operation(summary = "Get user by id")
    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);


    }
    @Operation(summary = "Get user by name")
    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByName(@PathVariable String userName) {
        User user = userService.getUserByUsername(userName);
        return ResponseEntity.ok().body(user);
    }
    @Operation(summary = "Create new user")
    @PostMapping
    public APIResponse<UserCreationResponse> addUser(@RequestBody UserCreationRequest request){
        var result = userService.createUser(request);
        return APIResponse.<UserCreationResponse>builder()
                .result(UserCreationResponse.builder()
                        .userName(result.getUsername())
                        .userId(result.getId())
                        .build())
                .build();
    }
    @Operation(summary = "Update existing users by id")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User user){
        userService.updateUser(userId, user);
        return ResponseEntity.ok().body(user);
    }
    @Operation(summary = "Delete users by id")
    @DeleteMapping
    public APIResponse<Object> deleteUserById(@RequestParam int userId){
        userService.deleteUser(userId);
        return APIResponse.builder()
                .result("User deleted")
                .build();
    }
    @PostMapping("/makeRe/{userId}")
    public APIResponse<Object> makeResident(@PathVariable int userId){
        userService.makeUserResident(userId);
        return APIResponse.builder()
                .message("Make user with id " + userId + " resident successfully")
                .build();
    }
    @PostMapping("/makeMa/{userId}")
    public APIResponse<Object> makeManager(@PathVariable int userId){
        userService.makeUserManager(userId);
        return APIResponse.builder()
                .message("Make user with id " + userId + " manager successfully")
                .build();
    }
    @PutMapping("/pwd")
    public APIResponse<Object> changePassword(@RequestBody ChangePasswordRequest request){
        userService.changePassword(request);
        return APIResponse.builder()
                .message("Change password successfully")
                .build();

    }
}
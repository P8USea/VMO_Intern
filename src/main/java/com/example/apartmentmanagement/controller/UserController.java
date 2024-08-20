package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.response.UserResponse;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.response.APIResponse;
import com.example.apartmentmanagement.service.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public APIResponse<List<User>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return APIResponse.<List<User>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);


    }
    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByName(@PathVariable String userName) {
        User user = userService.getUserByUsername(userName);
        return ResponseEntity.ok().body(user);
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok().body(user);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user){
        userService.updateUser(userId, user);
        return ResponseEntity.ok().body(user);
    }
}
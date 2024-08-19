package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
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
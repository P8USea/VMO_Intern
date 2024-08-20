package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.exception.*;
import com.example.apartmentmanagement.repository.UserRepository;
import com.hendisantika.usermanagement.dto.ChangePasswordForm;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('SCOPE_ASSMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private boolean checkUsernameAvailable(User requestUser) {
        return !userRepository.existsByUsername(requestUser.getUsername());
    }

    private boolean checkPasswordValid(User userRequest){
        if (userRequest.getConfirmPassword() == null || userRequest.getConfirmPassword().isEmpty()) {
            throw new UserException(ErrorCode.CONFIRMATION_CODE_BLANK);
        }

        if (!userRequest.getPassword().matches(userRequest.getConfirmPassword())) {
            throw new UserException(ErrorCode.CONFIRMATION_CODE_MISMATCH);
        }
        return true;
    }

    public User passwordGenerator(User requestUser){
        PasswordEncoder encodedPassword = new BCryptPasswordEncoder(10);
        requestUser.setPassword(encodedPassword.encode(requestUser.getPassword()));
        return requestUser;
    }

    public User createUser(User requestUser) throws UserException {
        if (checkUsernameAvailable(requestUser) && checkPasswordValid(requestUser)) {
            requestUser.setPassword(passwordGenerator(requestUser).getPassword());
            HashSet<Role> roles = new HashSet<>();
            roles.add(Role.RESIDENT);
            requestUser.setRoles(roles);
            return userRepository.save(requestUser);
        }
        throw new UserException(ErrorCode.UNCATEGORIZED_EXCEPTION);

    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(Long userId, User userDetails){
        User fromUser = getUserById(userId);
        mapUser(fromUser, userDetails);
        return userRepository.save(fromUser);
    }


    protected void mapUser(User from, User to) {
        to.setUsername(from.getUsername());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    public User changePassword(ChangePasswordForm form) throws Exception {
        User user = getUserById(form.getId());

        if (!user.getPassword().matches(form.getCurrentPassword())) {
            throw new UserException(ErrorCode.PASSWORD_INCORRECT);
        }

        if (user.getPassword().equals(form.getNewPassword())) {
            throw new UserException(ErrorCode.PASSWORD_REPEAT);
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            throw new UserException(ErrorCode.PASSWORD_MISMATCH);
        }

        String encodePassword = passwordGenerator(user).getPassword();
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }
}

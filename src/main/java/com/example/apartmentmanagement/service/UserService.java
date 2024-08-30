package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.ChangePasswordRequest;
import com.example.apartmentmanagement.dto.request.UserCreationRequest;
import com.example.apartmentmanagement.dto.response.UserCreationResponse;
import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.exception.*;
import com.example.apartmentmanagement.repository.UserRepository;
import com.hendisantika.usermanagement.dto.ChangePasswordForm;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final UserRepository userRepository;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAllActiveUsers();
    }

    private boolean checkUsernameAvailable(String requestedUsername) {
         if(userRepository.existsByUsername(requestedUsername))
             throw new UserException(ErrorCode.USER_EXISTED);
         return true;
    }

    private boolean checkPasswordValid(UserCreationRequest userRequest){
        if (userRequest.getConfirmPassword() == null || userRequest.getConfirmPassword().isEmpty()) {
            throw new UserException(ErrorCode.CONFIRMATION_CODE_BLANK);
        }

        if (!userRequest.getPassword().matches(userRequest.getConfirmPassword())) {
            throw new UserException(ErrorCode.CONFIRMATION_CODE_MISMATCH);
        }
        return true;
    }

    public String hashPassGenerator(String rawPassword){
        PasswordEncoder encodedPassword = new BCryptPasswordEncoder(10);
        return encodedPassword.encode(rawPassword);
    }

    public UserCreationResponse createUser(UserCreationRequest request) throws UserException {
        if (checkUsernameAvailable(request.getUsername()) && checkPasswordValid(request)) {
            User user = User.builder()
                    .username(request.getUsername())
                    .password(hashPassGenerator(request.getPassword()))
                    .roles(Set.of(Role.RESIDENT))
                    .build();
            userRepository.save(user);
            return new UserCreationResponse();
        }
        throw new UserException(ErrorCode.UNCATEGORIZED_EXCEPTION);

    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(int userId, User userDetails){
        User fromUser = getUserById(userId);
        mapUser(fromUser, userDetails);
        return userRepository.save(fromUser);
    }

    protected void mapUser(User from, User to) {
        to.setUsername(from.getUsername());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setRoles(from.getRoles());
    }

    public void deleteUser(int id) {
        User user = getUserById(id);
        user.set_Deleted(true);
        userRepository.save(user);
        log.info("Deleted user: " + user.getUsername());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    public User changePassword(ChangePasswordRequest request) throws UserException, AppException {
        User user = getUserById(request.getUserId());
        if (!hashPassGenerator(request.getOldPassword()).matches(user.getPassword())) {
            throw new UserException(ErrorCode.PASSWORD_INCORRECT);
        }
        if (hashPassGenerator(request.getOldPassword()).equals(request.getNewPassword())) {
            throw new UserException(ErrorCode.PASSWORD_REPEAT);
        }
        if(!request.getNewPassword().equals(request.getNewConfirmPassword())){
            throw new UserException(ErrorCode.CONFIRM_PASSWORD_MISMATCH);
        }

        String encodePassword = hashPassGenerator(request.getNewPassword());
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }

}

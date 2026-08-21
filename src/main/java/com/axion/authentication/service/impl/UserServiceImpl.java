package com.axion.authentication.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.Role;
import com.axion.authentication.entity.User;
import com.axion.authentication.exception.EmailAlreadyExistsException;
import com.axion.authentication.exception.UserNotFoundException;
import com.axion.authentication.exception.UsernameAlreadyExistsException;
import com.axion.authentication.repository.RoleRepository;
import com.axion.authentication.repository.UserRepository;
import com.axion.authentication.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User createUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    "Username '" + user.getUsername() + "' already exists."
            );
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email '" + user.getEmail() + "' already exists."
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role borrowerRole = roleRepository.findByName("BORROWER")
                .orElseThrow(() ->
                        new IllegalStateException("Default BORROWER role not found.")
                );

        user.getRoles().add(borrowerRole);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        // Initialize lazy roles within the transaction
        user.getRoles().size();

        return user;
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with username: " + username
                        )
                );
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
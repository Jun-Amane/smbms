/**
 * Package: moe.zzy040330.smbms.service.impl
 * File: AuthenticationServiceImpl.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:15
 * Description: The AuthenticationServiceImpl class implements authentication-related
 * operations, the AuthenticationService Interface.
 */
package moe.zzy040330.smbms.service.impl;

import moe.zzy040330.smbms.dto.LoginRequest;
import moe.zzy040330.smbms.entity.SecurityUser;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.mapper.UserMapper;
import moe.zzy040330.smbms.service.AuthenticationService;
import moe.zzy040330.smbms.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implements the AuthenticationService interface to provide business logic
 * for user authentication, token management, and password handling.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user based on the provided login request information.
     *
     * @param loginRequest an object containing user credentials for authentication
     * @return a SecurityUser object representing the authenticated user, enriched with security details
     */
    @Override
    public SecurityUser authenticate(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = userMapper.findByCode(loginRequest.getUsername());
        return new SecurityUser(user);

    }

    /**
     * Logs out a user by invalidating their current authentication token.
     *
     * @param token the authentication token that should be invalidated
     */
    @Override
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            jwtService.invalidateToken(jwt);
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }
    }

    /**
     * Encrypts all plaintext passwords in the testing database.
     * This method is intended for use in a testing environment to ensure
     * that any plaintext passwords are securely hashed.
     */
    @Override
    public void encryptAllPasswords() {

        List<User> users = userMapper.findAll();
        users.forEach(user -> System.out.println(passwordEncoder.encode(user.getPassword())));

        for (User user : users) {
            if (!user.getPassword().startsWith("$2a$")) {
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);
                var admin = new User();
                admin.setId(1L);
                userMapper.updateUserPassword(user.getId(), encryptedPassword, admin, new Date());
            }
        }
    }
}

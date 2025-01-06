/**
 * Package: moe.zzy040330.smbms.controller
 * File: AuthenticationController.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:18
 * Description: RESTful API for user login & logout
 */
package moe.zzy040330.smbms.controller;

import moe.zzy040330.smbms.dto.LoginRequest;
import moe.zzy040330.smbms.dto.LoginResponse;
import moe.zzy040330.smbms.entity.SecurityUser;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.AuthenticationService;
import moe.zzy040330.smbms.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        SecurityUser authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setUserCode(authenticatedUser.user().getCode());
        loginResponse.setUserName(authenticatedUser.user().getName());
        loginResponse.setUserId(authenticatedUser.user().getId());
        loginResponse.setUserRoleCode(authenticatedUser.user().getRole().getCode());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/encoded-all-pwd")
    public ResponseEntity<List<User>> alwet() {
        authenticationService.encryptAllPasswords();
        return null;
    }

}
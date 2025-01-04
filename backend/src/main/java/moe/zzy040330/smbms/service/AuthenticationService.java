/**
 * Package: moe.zzy040330.smbms.service
 * File: AuthenticationService.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:13
 * Description: The AuthenticationService interface provides methods for handling user
 * authentication processes, including login, logout, and password encryption
 * within the application. It defines the contract for any implementing class
 * to provide actual logic for these operations.
 */
package moe.zzy040330.smbms.service;

import moe.zzy040330.smbms.dto.LoginRequest;
import moe.zzy040330.smbms.entity.SecurityUser;

/**
 * Defines core authentication methods to manage and maintain security standards across the application.
 */
public interface AuthenticationService {
    /**
     * Authenticates a user based on the provided login request information.
     *
     * @param loginRequest an object containing user credentials for authentication
     * @return a SecurityUser object representing the authenticated user, enriched with security details
     */
    SecurityUser authenticate(LoginRequest loginRequest);

    /**
     * Logs out a user by invalidating their current authentication token.
     *
     * @param token the authentication token that should be invalidated
     */
    void logout(String token);

    /**
     * Encrypts all plaintext passwords in the testing database.
     * This method is intended for use in a testing environment to ensure
     * that any plaintext passwords are securely hashed.
     */
    void encryptAllPasswords();
}

/**
 * Package: moe.zzy040330.smbms.dto
 * File: LoginRequest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:14
 * Description: DTO for Login request
 */
package moe.zzy040330.smbms.dto;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

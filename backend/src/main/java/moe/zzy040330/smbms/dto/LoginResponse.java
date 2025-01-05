/**
 * Package: moe.zzy040330.smbms.dto
 * File: LoginResponse.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:19
 * Description: DTO for Login response
 */
package moe.zzy040330.smbms.dto;

public class LoginResponse {
    private String token;
    private String userName;
    private Long userId;
    private String userCode;
    private String userRoleCode;

    public LoginResponse() {
    }

    public LoginResponse(String token, String userName, Long userId, String userCode, String userRoleId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
        this.userCode = userCode;
        this.userRoleCode = userRoleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
    }
}

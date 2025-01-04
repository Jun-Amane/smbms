/**
 * Package: moe.zzy040330.smbms.dto
 * File: PasswordUpdateRequest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 11:10
 * Description: Password update request body.
 */
package moe.zzy040330.smbms.dto;

public class PasswordUpdateRequest {
    private String newPassword;

    public PasswordUpdateRequest() {}

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

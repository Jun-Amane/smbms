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
    private String oldPassword;
    private String newPassword;

    public PasswordUpdateRequest() {}

    public PasswordUpdateRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

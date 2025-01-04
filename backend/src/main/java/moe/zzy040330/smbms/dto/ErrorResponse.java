/**
 * Package: moe.zzy040330.smbms.controller.dto
 * File: ErrorResponse.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 11:02
 * Description: General error response body.
 */
package moe.zzy040330.smbms.dto;

public class ErrorResponse {
    private Integer status;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

/**
 * Package: moe.zzy040330.smbms.controller
 * File: ProviderRequest.java
 * Author: Wenqiang Chen
 * Date: 05/01/2025
 * Time: 11:49
 */
package moe.zzy040330.smbms.dto;

public class ProviderDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String fax;
    private String contact;

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}

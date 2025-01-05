/**
 * Package: moe.zzy040330.smbms.entity
 * File: Provider.java
 * Author: Xijian Sun
 * Date: 05/01/2025
 * Time: 10:12
 * Description: This class represents a Provider entity with basic information.
 */

package moe.zzy040330.smbms.entity;

import java.util.Date;

public class Provider {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String contact;
    private String phone;
    private String address;
    private String fax;
    private User createdBy;
    private Date creationDate;
    private User modifiedBy;
    private Date modificationDate;

    public Provider() {
    }

    public Provider(Long id, String code, String name,
                    String description, String contact, String phone, String address, String fax, User createdBy, Date creationDate, User modifiedBy, Date modificationDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.phone = phone;
        this.address = address;
        this.fax = fax;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifiedBy = modifiedBy;
        this.modificationDate = modificationDate;
    }

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}

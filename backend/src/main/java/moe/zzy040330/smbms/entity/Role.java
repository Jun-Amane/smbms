/**
 * Package: moe.zzy040330.smbms.entity
 * File: Role.java
 * Author: Ziyu ZHOU
 * Date: 03/01/2024
 * Time: 12:34
 * Description: This class represents a Role entity with basic information.
 */

package moe.zzy040330.smbms.entity;

import java.util.Date;

public class Role {
    private Long id;
    private String code;
    private String name;
    private User createdBy;
    private Date creationDate;
    private User modifiedBy;
    private Date modificationDate;

    public Role() {
    }

    public Role(Long id, String code, String name, User createdBy, Date creationDate, User modifiedBy, Date modificationDate) {
        this.id = id;
        this.code = code;
        this.name = name;
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

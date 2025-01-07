/**
 * Package: moe.zzy040330.smbms.dto
 * File: UserRequest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 11:30
 * Description: General User Entity is request bodies.
 */
package moe.zzy040330.smbms.dto;

public class UserDto {
    private Long id;
    private String code;
    private String name;
    private String password;
    private Integer gender;
    private String birthday;
    private String phone;
    private String address;
    private Long roleId;
    private Integer age;

    public UserDto() {
    }

    public UserDto(Long id, String code, String name, String password, Integer gender, String birthday, String phone, String address, Long roleId, Integer age) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.age = age;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

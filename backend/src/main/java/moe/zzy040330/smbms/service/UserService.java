/**
 * Package: moe.zzy040330.smbms.service
 * File: UserService.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 09:50
 * Description: The UserService interface defines the business operations specific to the
 * User entity. It extends the GenericService interface, inheriting basic CRUD
 * operations, and it adds user-specific functionalities such as authentication,
 * querying with pagination, and checking for existing user codes.
 */
package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Role;
import moe.zzy040330.smbms.entity.User;

import java.util.Date;
import java.util.List;

/**
 * UserService provides specific services for User entities including user
 * authentication and advanced searching functionality.
 */
public interface UserService extends GenericCrudService<User, Long> {

    /**
     * REPLACED WITH AuthenticationService
     *
     * @param userCode     the code of the user trying to log in
     * @param userPassword the password associated with the user code
     * @return the User object if authentication is successful; null otherwise
     */
    @Deprecated
    User login(String userCode, String userPassword);

    /**
     * Finds users based on specified name and role with pagination support.
     *
     * @param name     the name to search for (can be a partial match)
     * @param roleId   the role ID to filter users by role
     * @param pageNum  the page number for pagination
     * @param pageSize the number of records per page
     * @return a PageInfo object containing the list of users that match the query
     */
    PageInfo<User> findByQuery(String name, Long roleId, Integer pageNum, Integer pageSize);

    /**
     * Checks if a given user code already exists in the system.
     *
     * @param userCode the user code to check for existence
     * @return true if the user code exists, false otherwise
     */
    Boolean findIfUserCodeExists(String userCode);

    /**
     * Changes the password for a specific user identified by their ID.
     *
     * @param id          the ID of the user whose password is to be changed
     * @param oldPassword the old password
     * @param newPassword the new password to be set for the user
     * @param modifiedBy       the user who is modifying the entity
     * @param modificationDate the date when the entity is modified
     * @return true if the password change was successful, false otherwise
     */
    Boolean changePassword(Long id, String oldPassword, String newPassword, User modifiedBy, Date modificationDate);

    /**
     * @return the list of all roles.
     */
    List<Role> getRoleList();
}
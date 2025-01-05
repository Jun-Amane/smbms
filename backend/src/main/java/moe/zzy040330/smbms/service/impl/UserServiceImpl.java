/**
 * Package: moe.zzy040330.smbms.service.impl
 * File: UserServiceImpl.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 10:12
 * Description: implemented user service interface
 */
package moe.zzy040330.smbms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Role;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.mapper.GenericMapper;
import moe.zzy040330.smbms.mapper.RoleMapper;
import moe.zzy040330.smbms.mapper.UserMapper;
import moe.zzy040330.smbms.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends GenericCrudServiceImpl<User, Long> implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(GenericMapper<User, Long> genericMapper, UserMapper userMapper, RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
        super(genericMapper);

        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user with their user code and password.
     *
     * @param userCode     the code of the user trying to log in
     * @param userPassword the password associated with the user code
     * @return the User object if authentication is successful; null otherwise
     */
    @Deprecated
    @Override
    public User login(String userCode, String userPassword) {
        return this.userMapper.findByCodeAndPassword(userCode, userPassword);
    }

    /**
     * Finds users based on specified name and role with pagination support.
     *
     * @param name     the name to search for (can be a partial match)
     * @param roleId   the role ID to filter users by role
     * @param pageNum  the page number for pagination
     * @param pageSize the number of records per page
     * @return a PageInfo object containing the list of users that match the query
     */
    @Override
    public PageInfo<User> findByQuery(String name, Long roleId, Integer pageNum, Integer pageSize) {
        // Set up the pagination configuration
        PageHelper.startPage(pageNum, pageSize);

        // Execute the query with the given parameters
        List<User> users = userMapper.findAllUsersByQuery(name, roleId);

        // Wrap the result with PageInfo for pagination information
        return new PageInfo<>(users);
    }

    /**
     * Checks if a given user code already exists in the system.
     *
     * @param userCode the user code to check for existence
     * @return true if the user code exists, false otherwise
     */
    @Override
    public Boolean findIfUserCodeExists(String userCode) {
        return this.userMapper.findByCode(userCode) != null;
    }

    /**
     * Changes the password for a specific user identified by their ID.
     *
     * @param id          the ID of the user whose password is to be changed
     * @param newPassword the new password to be set for the user
     * @param modifiedBy       the user who is modifying the entity
     * @param modificationDate the date when the entity is modified
     * @return true if the password change was successful, false otherwise
     */
    @Override
    public Boolean changePassword(Long id, String newPassword, User modifiedBy, Date modificationDate) {
        return this.userMapper.updateUserPassword(id, passwordEncoder.encode(newPassword), modifiedBy, modificationDate) > 0;
    }

    /**
     * Inserts a new entity into the database. The password should be encoded.
     *
     * @param entity       the entity to be inserted
     * @return successful or not
     */
    @Override
    public Boolean insert(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return this.userMapper.insert(entity) > 0;
    }

    /**
     * @return the list of all roles.
     */
    @Override
    public List<Role> getRoleList() {
        return this.roleMapper.findAll();
    }
}

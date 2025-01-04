/**
 * Package: moe.zzy040330.smbms.mapper
 * File: UserMapper.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 01:10
 * Description: This interface defines the database operations specific to
 * the User entity. It extends the GenericMapper interface for basic CRUD
 * operations and adds additional methods tailored to user-specific queries.
 */

package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * UserMapper is responsible for handling SQL operations for the User entity.
 * It provides functionalities to find users by specific credentials,
 * update user information and perform queries based on user attributes.
 */
@Mapper
public interface UserMapper extends GenericMapper<User, Long> {

    /**
     * Finds a User by their code and password.
     *
     * @param code     the unique code associated with the user
     * @param password the password of the user, hash encoded
     * @return the User object if found, else null
     */
    User findByCodeAndPassword(@Param("code") String code, @Param("password") String password);

    /**
     * Retrieves a list of Users matching the specified name and role.
     *
     * @param name   the name of the user to search for
     * @param roleId the role ID to filter users by
     * @return a list of users that match the specified criteria
     */
    List<User> findAllUsersByQuery(@Param("name") String name, @Param("roleId") Long roleId);

    /**
     * Updates the password for a user specified by their ID.
     *
     * @param id       the ID of the user whose password is to be updated
     * @param password the new password to be set, hash encrypted
     * @param modifiedBy the user who modifies this record
     * @param modificationDate the timestamp when this record is modified
     * @return the number of rows affected by the update operation
     */
    Integer updateUserPassword(@Param("id") Long id, @Param("password") String password, @Param("modifiedBy") User modifiedBy, @Param("modificationDate") Date modificationDate);

    /**
     * Finds a User by their unique code.
     *
     * @param code the unique code associated with the user
     * @return the User object if found, else null
     */
    User findByCode(@Param("code") String code);

}

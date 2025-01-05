/**
 * Package: moe.zzy040330.smbms.service
 * File: UserServiceTest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 10:31
 * Description: JUnit test for UserService implementation.
 */
package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long testUserId;
    private final User modifiedByUser = new User();

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM smbms_user");

        User testUser = new User();
        testUser.setCode("testCode");
        testUser.setName("Test User");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setGender(1);
        testUser.setBirthday(new Date(946684800000L));
        testUser.setPhone("1234567890");
        testUser.setAddress("Test Address");
        modifiedByUser.setId(1L);
        testUser.setModifiedBy(modifiedByUser);
        testUser.setModificationDate(new Date());
        testUser.setCreatedBy(modifiedByUser);
        testUser.setCreationDate(new Date());

        userService.insert(testUser);
        testUserId = testUser.getId();

        assertNotNull(testUser.getName());
    }
/*
    @Test
    void testLogin() {
        User user = userService.login("testCode", "password");
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }*/

    @Test
    void testFindByQuery() {
        PageInfo<User> pageInfo = userService.findByQuery("Test", null, 1, 10);
        assertNotNull(pageInfo);
        assertFalse(pageInfo.getList().isEmpty());
        assertEquals(1, pageInfo.getList().size());
    }

    @Test
    void testFindIfUserCodeExists() {
        Boolean exists = userService.findIfUserCodeExists("testCode");
        assertTrue(exists);

        Boolean notExists = userService.findIfUserCodeExists("nonExistentCode");
        assertFalse(notExists);
    }

    @Test
    void testChangePassword() {
        String newPassword = "newPassword";
        Boolean success = userService.changePassword(testUserId, newPassword, modifiedByUser, new Date());
        assertTrue(success);

        User updatedUser = userService.findById(testUserId);
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    public void testFindById() {
        User user = userService.findById(testUserId);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }

    @Test
    public void testFindAll() {
        List<User> users = userService.findAll();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    public void testInsert() {
        User newUser = new User();
        newUser.setCode("newCode");
        newUser.setName("New User");
        newUser.setPassword(passwordEncoder.encode("newpassword"));
        newUser.setGender(2);
        newUser.setBirthday(new Date(946684800000L));
        newUser.setPhone("0987654321");
        newUser.setAddress("New Address");
        newUser.setModifiedBy(modifiedByUser);
        newUser.setModificationDate(new Date());
        newUser.setCreatedBy(modifiedByUser);
        newUser.setCreationDate(new Date());

        var result = userService.insert(newUser);
        assertEquals(true, result);
        assertNotNull(newUser.getId());
    }

    @Test
    public void testUpdate() {
        User existingUser = userService.findById(testUserId);
        existingUser.setName("Updated Name");
        existingUser.setModifiedBy(modifiedByUser);
        existingUser.setModificationDate(new Date());

        var result = userService.update(existingUser);
        assertEquals(true,result);

        User updatedUser = userService.findById(testUserId);
        assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    public void testDeleteById() {
        var result = userService.deleteById(testUserId);
        assertEquals(true, result);

        User deletedUser = userService.findById(testUserId);
        assertNull(deletedUser);
    }
}


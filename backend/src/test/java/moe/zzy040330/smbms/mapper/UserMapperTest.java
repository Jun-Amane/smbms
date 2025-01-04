/**
 * Package: moe.zzy040330.smbms.mapper
 * File: UserMapperTest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 01:40
 * Description: JUnit test for UserMapper class.
 */

package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMybatis
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

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
        testUser.setPassword("password");
        testUser.setGender(1);
        testUser.setBirthday(new Date(946684800000L));
        testUser.setPhone("1234567890");
        testUser.setAddress("Test Address");
        modifiedByUser.setId(1L);

        userMapper.insert(testUser, modifiedByUser, new Date());
        testUserId = testUser.getId();

        assertNotNull(testUser.getName());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindById() {
        User user = userMapper.findById(testUserId);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }

    @Test
    public void testFindAll() {
        List<User> users = userMapper.findAll();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    public void testInsert() {
        User newUser = new User();
        newUser.setCode("newCode");
        newUser.setName("New User");
        newUser.setPassword("newpassword");
        newUser.setGender(2);
        newUser.setBirthday(new Date(946684800000L));
        newUser.setPhone("0987654321");
        newUser.setAddress("New Address");

        int rowsAffected = userMapper.insert(newUser, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);
        assertNotNull(newUser.getId());
    }

    @Test
    public void testUpdate() {
        User existingUser = userMapper.findById(testUserId);
        existingUser.setName("Updated Name");

        int rowsAffected = userMapper.update(existingUser, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);

        User updatedUser = userMapper.findById(testUserId);
        assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    public void testDeleteById() {
        int rowsAffected = userMapper.deleteById(testUserId);
        assertEquals(1, rowsAffected);

        User deletedUser = userMapper.findById(testUserId);
        assertNull(deletedUser);
    }

    @Test
    public void testFindByCodeAndPassword() {
        User user = userMapper.findByCodeAndPassword("testCode", "password");
        assertNotNull(user);
    }

    @Test
    public void testFindAllUsersByQuery() {
        List<User> result = userMapper.findAllUsersByQuery("Test", null);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testUpdateUserPassword() {
        String newPassword = "newPassword";
        int rowsAffected = userMapper.updateUserPassword(testUserId, newPassword, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);

        User user = userMapper.findById(testUserId);
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    public void testFindByCode() {
        User user = userMapper.findByCode("testCode");
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }
}

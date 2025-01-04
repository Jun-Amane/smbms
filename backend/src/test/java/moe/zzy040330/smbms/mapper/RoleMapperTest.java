/**
 * Package: moe.zzy040330.smbms.mapper
 * File: RoleMapperTest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 12:01
 * Description: JUnit test for role mapper
 */
package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.Role;
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
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long testRoleId;

    private final User modifiedByUser = new User();

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM smbms_role");

        Role testRole = new Role();
        testRole.setCode("testCode");
        testRole.setName("Test Role");
        modifiedByUser.setId(1L);

        roleMapper.insert(testRole, modifiedByUser, new Date());
        testRoleId = testRole.getId();

        assertNotNull(testRole.getName());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindById() {
        Role role = roleMapper.findById(testRoleId);
        assertNotNull(role);
        assertEquals("Test Role", role.getName());
    }

    @Test
    public void testFindAll() {
        List<Role> roles = roleMapper.findAll();
        assertFalse(roles.isEmpty());
        assertEquals(1, roles.size());
    }

    @Test
    public void testInsert() {
        Role newRole = new Role();
        newRole.setCode("newCode");
        newRole.setName("New Role");

        int rowsAffected = roleMapper.insert(newRole, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);
        assertNotNull(newRole.getId());
    }

    @Test
    public void testUpdate() {
        Role existingRole = roleMapper.findById(testRoleId);
        existingRole.setName("Updated Name");

        int rowsAffected = roleMapper.update(existingRole, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);

        Role updatedRole = roleMapper.findById(testRoleId);
        assertEquals("Updated Name", updatedRole.getName());
    }

    @Test
    public void testDeleteById() {
        int rowsAffected = roleMapper.deleteById(testRoleId);
        assertEquals(1, rowsAffected);

        Role deletedRole = roleMapper.findById(testRoleId);
        assertNull(deletedRole);
    }

}

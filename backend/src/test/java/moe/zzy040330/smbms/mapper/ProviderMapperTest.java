/**
 * Package: moe.zzy040330.smbms.mapper
 * File: ProviderMapperTest.java
 * Author: Xijian Sun
 * Date: 05/01/2025
 * Time: 15:29
 * Description: JUnit test for provider mapper
 */
package moe.zzy040330.smbms.mapper;


import moe.zzy040330.smbms.entity.Provider;
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
public class ProviderMapperTest {
    @Autowired
    private ProviderMapper providerMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long testProviderId;

    private final User modifiedByUser = new User();

    @BeforeEach
    public void setUp(){
        jdbcTemplate.execute("DELETE FROM smbms_provider");

        Provider testProvider = new Provider();
        testProvider.setCode("testCode");
        testProvider.setName("Test Provider");
        testProvider.setDescription("Test Description");
        testProvider.setContact("testContact");
        testProvider.setPhone("1234567890");
        testProvider.setAddress("Test Address");
        testProvider.setFax("Test Fax");
        modifiedByUser.setId(1L);
        testProvider.setModifiedBy(modifiedByUser);
        testProvider.setModificationDate(new Date());
        testProvider.setCreatedBy(modifiedByUser);
        testProvider.setCreationDate(new Date());

        providerMapper.insert(testProvider);
        testProviderId = testProvider.getId();

        assertNotNull(testProvider.getName());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindById() {
        Provider provider = providerMapper.findById(testProviderId);
        assertNotNull(provider);
        assertEquals("Test Provider", provider.getName());
    }

    @Test
    public void testFindAll(){
        List<Provider> providers = providerMapper.findAll();
        assertFalse(providers.isEmpty());
        assertEquals(1,providers.size());
    }

    @Test
    public void testInsert(){
        Provider newProvider = new Provider();
        newProvider.setCode("newCode");
        newProvider.setName("new Provider");
        newProvider.setDescription("new Description");
        newProvider.setContact("newContact");
        newProvider.setPhone("0987654321");
        newProvider.setAddress("new Address");
        newProvider.setFax("new Fax");
        newProvider.setModifiedBy(modifiedByUser);
        newProvider.setCreationDate(new Date());
        newProvider.setCreatedBy(modifiedByUser);
        newProvider.setModificationDate(new Date());

        int rowsAffected = providerMapper.insert(newProvider);
        assertEquals(1,rowsAffected);
        assertNotNull(newProvider.getId());
    }

    @Test
    public void testUpdate(){
        Provider existingProvider = providerMapper.findById(testProviderId);
        existingProvider.setName("Updated Name");
        existingProvider.setModifiedBy(modifiedByUser);
        existingProvider.setModificationDate(new Date());

        int rowsAffected = providerMapper.update(existingProvider);
        assertEquals(1,rowsAffected);

        Provider updatedProvider = providerMapper.findById(testProviderId);
        assertEquals("Updated Name",updatedProvider.getName());
    }

    @Test
    public void testDeleteById(){
        int rowsAffected = providerMapper.deleteById(testProviderId);
        assertEquals(1,rowsAffected);

        Provider deletedProvider = providerMapper.findById(testProviderId);
        assertNull(deletedProvider);
    }

    @Test
    public void testFindAllProvidersByQuery(){
        List<Provider> result = providerMapper.findAllProvidersByQuery("Test",null);
        assertFalse(result.isEmpty());
    }

}

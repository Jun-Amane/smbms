package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
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

/**
 * Package: moe.zzy040330.smbms.service
 * File: ProviderServiceTest.java
 * Author: Xijian Sun
 * Date: 05/01/2025
 * Time: 16:03
 * Description: JUnit test for ProviderService implementation.
 */

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMybatis
public class ProviderServiceTest {
    @Autowired
    private ProviderService providerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long testProviderId;
    private final User modifiedByUser = new User();

    @BeforeEach
    public void setUp() {
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
        testProvider.setCreatedBy(modifiedByUser);
        testProvider.setModificationDate(new Date());
        testProvider.setCreationDate(new Date());

        providerService.insert(testProvider);
        testProviderId = testProvider.getId();

        assertNotNull(testProvider.getName());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindById() {
        Provider provider = providerService.findById(testProviderId);
        assertNotNull(provider);
        assertEquals("Test Provider", provider.getName());
    }

    @Test
    public void testFindAll() {
        List<Provider> providers = providerService.findAll();
        assertFalse(providers.isEmpty());
        assertEquals(1, providers.size());
    }

    @Test
    public void testInsert() {
        Provider newProvider = new Provider();
        newProvider.setCode("newCode");
        newProvider.setName("new Provider");
        newProvider.setDescription("new Description");
        newProvider.setContact("newContact");
        newProvider.setPhone("0987654321");
        newProvider.setAddress("new Address");
        newProvider.setFax("new Fax");
        newProvider.setModifiedBy(modifiedByUser);
        newProvider.setCreatedBy(modifiedByUser);
        newProvider.setCreationDate(new Date());
        newProvider.setModificationDate(new Date());

        var succeed = providerService.insert(newProvider);
        assertEquals(true, succeed);
        assertNotNull(newProvider.getId());
    }

    @Test
    public void testUpdate() {
        Provider existingProvider = providerService.findById(testProviderId);
        existingProvider.setName("Updated Name");
        existingProvider.setModifiedBy(modifiedByUser);
        existingProvider.setModificationDate(new Date());

        var succeed = providerService.update(existingProvider);
        assertEquals(true, succeed);

        Provider updatedProvider = providerService.findById(testProviderId);
        assertEquals("Updated Name", updatedProvider.getName());
    }

    @Test
    public void testDeleteById() {
        var succeed = providerService.deleteById(testProviderId);
        assertEquals(true, succeed);

        Provider deletedProvider = providerService.findById(testProviderId);
        assertNull(deletedProvider);
    }

    @Test
    public void testFindAllProvidersByQuery() {
        PageInfo<Provider> pageInfo = providerService.findProviderByQuery("Test", null, 1, 10);
        assertNotNull(pageInfo);
        assertFalse(pageInfo.getList().isEmpty());
        assertEquals(1, pageInfo.getList().size());
    }

    @Test
    public void testDeleteProviderById() {
        var succeed = providerService.deleteProviderById(testProviderId);
        assertEquals(true, succeed);
    }
}

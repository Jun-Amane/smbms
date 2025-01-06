package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMybatis
public class BillMapperTest {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private ProviderMapper providerMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long testBillId;
    private final Provider testProvider = new Provider();

    private final User modifiedByUser = new User();

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM smbms_bill");
        jdbcTemplate.execute("DELETE FROM smbms_provider");

        testProvider.setCode("testCode");
        testProvider.setName("Test Provider");
        testProvider.setDescription("Test Description");
        testProvider.setContact("testContact");
        testProvider.setPhone("1234567890");
        testProvider.setAddress("Test Address");
        testProvider.setFax("Test Fax");
        modifiedByUser.setId(1L);

        testProvider.setModificationDate(new Date());
        testProvider.setCreatedBy(modifiedByUser);
        testProvider.setCreationDate(new Date());
        testProvider.setModifiedBy(modifiedByUser);

        providerMapper.insert(testProvider);

        assertNotNull(testProvider.getName());

        Bill testBill = new Bill();
        testBill.setCode("BILL001");
        testBill.setProductName("Test Product");
        testBill.setProductDescription("Test Description");
        testBill.setProductUnit("pcs");
        testBill.setProductCount(BigDecimal.valueOf(100));
        testBill.setTotalPrice(BigDecimal.valueOf(500.00));
        testBill.setIsPaid(1);
        testBill.setProvider(testProvider);
        testBill.setModificationDate(new Date());
        testBill.setCreatedBy(modifiedByUser);
        testBill.setCreationDate(new Date());
        testBill.setModifiedBy(modifiedByUser);
        modifiedByUser.setId(1L);

        billMapper.insert(testBill);
        testBillId = testBill.getId();

        assertNotNull(testBill.getProductName());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindById() {
        Bill bill = billMapper.findById(testBillId);
        assertNotNull(bill);
        assertEquals("Test Product", bill.getProductName());
    }

    @Test
    public void testFindAll() {
        List<Bill> bills = billMapper.findAll();
        assertFalse(bills.isEmpty());
        assertEquals(1, bills.size());
    }

    @Test
    public void testInsert() {
        Bill newBill = new Bill();
        newBill.setCode("BILL002");
        newBill.setProductName("New Product");
        newBill.setProductDescription("New Description");
        newBill.setProductUnit("kg");
        newBill.setProductCount(BigDecimal.valueOf(200));
        newBill.setTotalPrice(BigDecimal.valueOf(1000.00));
        newBill.setIsPaid(0);
        newBill.setProvider(testProvider);
        newBill.setModificationDate(new Date());
        newBill.setModifiedBy(modifiedByUser);
        newBill.setCreationDate(new Date());
        newBill.setCreatedBy(modifiedByUser);

        int rowsAffected = billMapper.insert(newBill);
        assertEquals(1, rowsAffected);
        assertNotNull(newBill.getId());
    }

    @Test
    public void testUpdate() {
        Bill existingBill = billMapper.findById(testBillId);
        existingBill.setProductName("Updated Product");
        existingBill.setModificationDate(new Date());
        existingBill.setModifiedBy(modifiedByUser);

        int rowsAffected = billMapper.update(existingBill);
        assertEquals(1, rowsAffected);

        Bill updatedBill = billMapper.findById(testBillId);
        assertEquals("Updated Product", updatedBill.getProductName());
    }

    @Test
    public void testFindByQuery() {
        List<Bill> result = billMapper.findAllBillsByQuery("BILL00", null, null, null, null, null);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testDeleteById() {
        int rowsAffected = billMapper.deleteById(testBillId);
        assertEquals(1, rowsAffected);

        Bill deletedBill = billMapper.findById(testBillId);
        assertNull(deletedBill);
    }

}

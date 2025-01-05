package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.Bill;
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
    private JdbcTemplate jdbcTemplate;

    private Long testBillId;

    private final User modifiedByUser = new User();

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM smbms_bill");

        Bill testBill = new Bill();
        testBill.setCode("BILL001");
        testBill.setProductName("Test Product");
        testBill.setProductDescription("Test Description");
        testBill.setProductUnit("pcs");
        testBill.setProductCount(BigDecimal.valueOf(100));
        testBill.setTotalPrice(BigDecimal.valueOf(500.00));
        testBill.setIsPaid(1);
        testBill.setProvideId(1L);
        modifiedByUser.setId(1L);

        billMapper.insert(testBill, modifiedByUser, new Date());
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
        newBill.setProvideId(2L);

        int rowsAffected = billMapper.insert(newBill, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);
        assertNotNull(newBill.getId());
    }

    @Test
    public void testUpdate() {
        Bill existingBill = billMapper.findById(testBillId);
        existingBill.setProductName("Updated Product");

        int rowsAffected = billMapper.update(existingBill, modifiedByUser, new Date());
        assertEquals(1, rowsAffected);

        Bill updatedBill = billMapper.findById(testBillId);
        assertEquals("Updated Product", updatedBill.getProductName());
    }

    @Test
    public void testDeleteById() {
        int rowsAffected = billMapper.deleteById(testBillId);
        assertEquals(1, rowsAffected);

        Bill deletedBill = billMapper.findById(testBillId);
        assertNull(deletedBill);
    }

    @Test
    public void testFindByQuery() {
        List<Bill> result = billMapper.findAllBillsByQuery();
        assertFalse(result.isEmpty());
    }
}

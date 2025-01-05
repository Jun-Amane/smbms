package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.mapper.BillMapper;
import moe.zzy040330.smbms.service.impl.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillServiceTest {
    @Mock
    private BillMapper billMapper;

    @InjectMocks
    private BillServiceImpl billService;

    private Bill testBill;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize a sample Bill object for testing
        testBill = new Bill();
        testBill.setId(1L);
        testBill.setCode("BILL001");
        testBill.setProductName("Test Product");
        testBill.setProductDescription("This is a test product");
        testBill.setProductUnit("pcs");
        testBill.setProductCount(BigDecimal.valueOf(10));
        testBill.setTotalPrice(BigDecimal.valueOf(100.00));
        testBill.setIsPaid(1);
        testBill.setProvideId(1L);
        User testUser = new User();
        testUser.setId(1L);
        testBill.setCreatedBy(testUser);
        testBill.setCreationDate(new Date());
        testBill.setModifiedBy(testUser);
        testBill.setModificationDate(new Date());
    }
    @Test
    public void testGetBillList() {
        // Mock the behavior of billMapper.findAllBillsByQuery
        Bill condition = new Bill();
        List<Bill> mockBillList = Arrays.asList(testBill);
        when(billMapper.findAllBillsByQuery()).thenReturn(mockBillList);
        // Call the service method
        PageInfo<Bill> pageInfo = billService.getBillList(condition, 1, 10);
        // Assertions
        assertNotNull(pageInfo);
        assertEquals(1, pageInfo.getTotal());
        assertEquals(1, pageInfo.getList().size());
        assertEquals("Test Product", pageInfo.getList().get(0).getProductName());
        // Verify interaction with the mock
        verify(billMapper, times(1)).findAllBillsByQuery();
    }
    @Test
    public void testGetBillListEmptyResult() {
        // Mock empty result
        Bill condition = new Bill();
        when(billMapper.findAllBillsByQuery()).thenReturn(Arrays.asList());
        // Call the service method
        PageInfo<Bill> pageInfo = billService.getBillList(condition, 1, 10);
        // Assertions
        assertNotNull(pageInfo);
        assertTrue(pageInfo.getList().isEmpty());
        assertEquals(0, pageInfo.getTotal());

        // Verify interaction with the mock
        verify(billMapper, times(1)).findAllBillsByQuery();
    }
    @Test
    public void testPaginationBehavior() {
        // Mock a list of bills for pagination
        List<Bill> mockBillList = Arrays.asList(
                new Bill(1L, "BILL001", "Product1", "Description1", "pcs", BigDecimal.ONE, BigDecimal.TEN, 1, null, null, new Date(), null, new Date(), 1L),
                new Bill(2L, "BILL002", "Product2", "Description2", "pcs", BigDecimal.ONE, BigDecimal.TEN, 1, null, null, new Date(), null, new Date(), 1L)
        );
        when(billMapper.findAllBillsByQuery()).thenReturn(mockBillList);
        // Call the service method
        PageInfo<Bill> pageInfo = billService.getBillList(new Bill(), 1, 2);
        // Assertions
        assertNotNull(pageInfo);
        assertEquals(2, pageInfo.getList().size());
        assertEquals("Product1", pageInfo.getList().get(0).getProductName());
        assertEquals("Product2", pageInfo.getList().get(1).getProductName());
        // Verify interaction with the mock
        verify(billMapper, times(1)).findAllBillsByQuery();
    }
}

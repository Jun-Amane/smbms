/**
 * Package: moe.zzy040330.smbms.service.impl
 * File: BillServiceImpl.java
 * Author: Xiaoteng Ma & Mingxue Li
 * Date: 05/01/2025
 * Description: implemented bill service interface
 */
package moe.zzy040330.smbms.service.impl;

import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.mapper.BillMapper;
import moe.zzy040330.smbms.mapper.GenericMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import moe.zzy040330.smbms.service.BillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl extends GenericCrudServiceImpl<Bill, Long> implements BillService {
    private final BillMapper billMapper;//bill mapper, use constructor injection

    public BillServiceImpl(GenericMapper<Bill, Long> genericMapper, BillMapper billMapper) {
        super(genericMapper);
        this.billMapper = billMapper;
    }

    /**
     * Finds bills based on specified query conditions with pagination support.
     *
     * @param pageNum  the page number for pagination
     * @param pageSize the number of records per page
     * @return a PageInfo object containing the list of bills that match the query
     */
    @Override
    public PageInfo<Bill> getBillList(String code, String productName, String productDesc, String providerCode,
                                      String providerName, Integer isPaid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Bill> bills = billMapper.findAllBillsByQuery(code, productName, productDesc,
                providerCode, providerName, isPaid);
        return new PageInfo<>(bills);
    }
}

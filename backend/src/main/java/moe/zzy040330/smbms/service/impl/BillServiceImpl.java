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
     * Get a paginated list of bills based on query conditions.
     *
     * @param condition query conditions packed into a Bill object
     * @param pageNum   current page number
     * @param pageSize  number of records per page
     * @return a PageInfo object containing the paginated list of bills
     */
    @Override
    public PageInfo<Bill> getBillList(Bill condition, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Bill> bills = billMapper.findAllBillsByQuery(condition);
        return new PageInfo<>(bills);
    }
}

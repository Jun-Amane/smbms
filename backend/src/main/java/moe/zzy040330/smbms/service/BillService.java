package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Bill;

/**
 * BillService provides specific services for Bill entities, including advanced searching functionality.
 */
public interface BillService extends GenericCrudService<Bill, Long> {

    /**
     * Finds bills based on specified query conditions with pagination support.
     *
     * @param pageNum   the page number for pagination
     * @param pageSize  the number of records per page
     * @return a PageInfo object containing the list of bills that match the query
     */
    PageInfo<Bill> getBillList(String code, String productName, String productDesc, String providerCode,
                               String providerName, Integer isPaid, Integer pageNum, Integer pageSize);
}

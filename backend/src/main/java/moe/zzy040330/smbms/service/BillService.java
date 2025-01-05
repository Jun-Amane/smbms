package moe.zzy040330.smbms.service;
import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.service.GenericCrudService;
/**
 * BillService provides specific services for Bill entities, including advanced searching functionality.
 */
public interface BillService extends GenericCrudService<Bill, Long> {
    /**
     * Finds bills based on specified query conditions with pagination support.
     *
     * @param condition the query conditions packed into a Bill object
     * @param pageNum   the page number for pagination
     * @param pageSize  the number of records per page
     * @return a PageInfo object containing the list of bills that match the query
     */
    PageInfo<Bill> getBillList(Bill condition, Integer pageNum, Integer pageSize);
}
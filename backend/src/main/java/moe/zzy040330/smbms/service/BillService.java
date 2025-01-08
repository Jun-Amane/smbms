/**
 * Package: moe.zzy040330.smbms.service
 * File: BillService.java
 * Author: Xiaoteng Ma & Mingxue Li
 * Date: 05/01/2025
 * Description: The BillService interface defines the business operations specific to the
 * Bill entity. It extends the GenericService interface, inheriting basic CRUD
 * operations, and it adds bill-specific functionalities.
 */
package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.dto.BillStatsDto;
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
    PageInfo<Bill> findBillByQuery(String code, String productName, String productDesc, String providerCode,
                                   String providerName, Integer isPaid, Integer pageNum, Integer pageSize,
                                   Integer minQuantity, Integer maxQuantity,Double minPrice,Double maxPrice,
                                   String orderBy,String orderDirection);

    /**
     *  statistic info will be shown in Bill page
     *
     * @return just the DTO
     */
    BillStatsDto getBillStats();
}

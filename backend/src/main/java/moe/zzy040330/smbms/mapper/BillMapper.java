/**
 * Package: moe.zzy040330.smbms.mapper
 * File: BillMapper.java
 * Author: Mingxue Li & Xiaoteng Ma
 * Date: 05/01/2025
 * Description: This interface defines the database operations specific to
 * the Bill entity. It extends the GenericMapper interface for basic CRUD
 * operations but no specific operations required.
 */
package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * BillMapper provides specific data access methods for the Bill entity, including advanced
 * querying and counting bills related to a specific provider.
 */
@Mapper
public interface BillMapper extends GenericMapper<Bill, Long> {
    /**
     * Finds the count of bills associated with a specific provider.
     *
     * @param providerId the ID of the provider
     * @return the number of bills associated with the given provider
     */
    Integer findBillCountByProviderId(@Param("providerId") Long providerId);

    /**
     * Finds bills based on specified query conditions.
     *
     * @return a list of bills matching the query conditions
     */
    List<Bill> findAllBillsByQuery(@Param("code") String code,
                                   @Param("productName") String productName,
                                   @Param("productDesc") String productDesc,
                                   @Param("providerCode") String providerCode,
                                   @Param("providerName") String providerName,
                                   @Param("isPaid") Integer isPaid,

                                   @Param("minQuantity") Integer minQuantity,
                                   @Param("maxQuantity") Integer maxQuantity,
                                   @Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   @Param("orderBy") String orderBy,
                                   @Param("orderDirection") String orderDirection


    );
}

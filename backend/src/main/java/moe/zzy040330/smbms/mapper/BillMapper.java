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
                                   @Param("isPaid") Integer isPaid
    );
}

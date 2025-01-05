package moe.zzy040330.smbms.mapper;
import moe.zzy040330.smbms.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    int findBillCountByProviderId(@Param("providerId") Long providerId);
    /**
     * Finds bills based on specified query conditions.
     *
     * @param condition the query conditions packed into a Bill object (supports fuzzy search)
     * @return a list of bills matching the query conditions
     */
    List<Bill> findAllBillsByQuery(@Param("condition") Bill condition);
}

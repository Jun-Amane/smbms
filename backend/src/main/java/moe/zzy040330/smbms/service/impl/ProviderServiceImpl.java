/**
 * Package: moe.zzy040330.smbms.service.impl
 * File: ProviderServiceImpl.java
 * Author: Xijian Sun
 * Date: 05/01/2025
 * Time: 11:44
 * Description: implemented provider service interface
 */
package moe.zzy040330.smbms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.mapper.BillMapper;
import moe.zzy040330.smbms.mapper.GenericMapper;
import moe.zzy040330.smbms.mapper.ProviderMapper;
import moe.zzy040330.smbms.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl extends GenericCrudServiceImpl<Provider, Long> implements ProviderService {
    private final ProviderMapper providerMapper;
    private final BillMapper billMapper;

    public ProviderServiceImpl(GenericMapper<Provider,Long> genericMapper, ProviderMapper providerMapper, BillMapper billMapper) {
        super(genericMapper);
        this.providerMapper = providerMapper;
        this.billMapper = billMapper;
    }

    /**
     * Finds providers based on specified name and code with pagination support.
     *
     * @param name     the name to search for (can be a partial match)
     * @param code     the code to search for (can be a partial match)
     * @param pageNum  the page number for pagination
     * @param pageSize the number of records per page
     * @return a PageInfo object containing the list of providers that match the query
     */
    @Override
    public PageInfo<Provider> findProviderByQuery(String name, String code, Integer pageNum, Integer pageSize) {
        // Set up the pagination configuration
        PageHelper.startPage(pageNum, pageSize);

        List<Provider> providers = this.providerMapper.findAllProvidersByQuery(name,code);
        return new PageInfo<>(providers);
    }

    /**
     * Deleting provider by specified id
     * Before operation, use billMapper.findBillCountByProviderId() to find out the number of bills related to the provider.
     * If there are bills in system related to the provider, then the provider cannot be deleted.
     *
     * @param id the ID of the provider who will be deleted
     * @return   successful or not
     */
    @Override
    public Boolean deleteProviderById(Long id) {
        if(billMapper.findBillCountByProviderId(id) == 0) {
            return providerMapper.deleteById(id) > 0;
        }else{
            throw new IllegalArgumentException("The provider with bills cannot be deleted.");
        }
    }
}

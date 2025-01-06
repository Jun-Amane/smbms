/**
 * Package: moe.zzy040330.smbms.service
 * File: ProviderService.java
 * Author: Xijian Sun
 * Date: 05/01/2025
 * Time: 10:55
 * Description: The ProviderService interface defines the business operations specific to the
 * Provider entity. It extends the GenericService interface, inheriting basic CRUD
 * operations, and it adds provider-specific functionalities such as authentication,
 * querying with pagination, and checking for existing provider codes.
 */
package moe.zzy040330.smbms.service;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.entity.Provider;


/**
 * ProviderService provides specific services for Provider entities including provider
 * advanced searching functionality and deleting Provider
 */
public interface ProviderService extends GenericCrudService<Provider,Long> {
    /**
     * Finds providers based on specified name and code with pagination support.
     *
     * @param name      the name to search for (can be a partial match)
     * @param code      the code to search for (can be a partial match)
     * @param pageNum   the page number for pagination
     * @param pageSize  the number of records per page
     * @return a PageInfo object containing the list of providers that match the query
     */
    PageInfo<Provider> queryProviders(String name, String code, Integer pageNum, Integer pageSize);

    /**
     * Deleting provider by specified id
     * Before operation, use billMapper.findBillCountByProviderId() to find out the number of bills related to the provider.
     * If there are bills in system related to the provider, then the provider cannot be deleted.
     *
     * @param id    the ID of the provider who will be deleted
     * @return      successful or not
     */
    Boolean deleteProviderById(Long id);
}

package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.Provider;

import java.util.Date;
import java.util.List;


/**
 * Package: moe.zzy040330.smbms.mapper
 * File: ProviderMapper.java
 * Author: Anji Yu
 * Date: 05/01/2025
 * Time: 10:49
 * Description: TODO: change me
 */
public interface ProviderMapper extends GenericMapper<Provider,Long>{
    /**
     *Get provider list by query condition
     *
     * @param name the name of the provider to search for
     * @param code the unique code associated with the provider
     * @return a list of providers that match the specified criteria
     */
    List<Provider> findAllProvidersByQuery(String name, String code);

}

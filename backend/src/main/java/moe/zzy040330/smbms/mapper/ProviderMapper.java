/**
 * Package: moe.zzy040330.smbms.mapper
 * File: ProviderMapper.java
 * Author: Anji Yu
 * Date: 05/01/2025
 * Time: 10:49
 * Description: This interface defines the database operations specific to
 * the Provider entity. It extends the GenericMapper interface for basic CRUD
 * operations but no specific operations required.
 */
package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.Provider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProviderMapper extends GenericMapper<Provider,Long>{
    /**
     *Get provider list by query condition
     *
     * @param name the name of the provider to search for
     * @param code the unique code associated with the provider
     * @return a list of providers that match the specified criteria
     */
    List<Provider> findAllProvidersByQuery(@Param("name") String name, @Param("code") String code);

}

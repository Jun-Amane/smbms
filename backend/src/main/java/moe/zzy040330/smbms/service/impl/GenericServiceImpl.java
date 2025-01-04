/**
 * Package: moe.zzy040330.smbms.service.impl
 * File: GenericServiceImpl.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 10:08
 * Description: implemented generic service interface
 */
package moe.zzy040330.smbms.service.impl;

import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.mapper.GenericMapper;
import moe.zzy040330.smbms.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GenericServiceImpl<T, PK> implements GenericService<T, PK> {

    private final GenericMapper<T, PK> genericMapper;

    public GenericServiceImpl(GenericMapper<T, PK> genericMapper) {
        this.genericMapper = genericMapper;
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param entity       the entity to be inserted
     * @param createdBy    the user who is creating the entity
     * @param creationDate the date when the entity is created
     * @return successful or not
     */
    @Override
    public Boolean insert(T entity, User createdBy, Date creationDate) {
        return this.genericMapper.insert(entity, createdBy, creationDate) > 0;
    }

    /**
     * Deletes an entity identified by its primary key from the database.
     *
     * @param id the primary key of the entity to be deleted
     * @return successful or not
     */
    @Override
    public Boolean deleteById(PK id) {
        return this.genericMapper.deleteById(id) > 0;
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity           the entity to be updated
     * @param modifiedBy       the user who is modifying the entity
     * @param modificationDate the date when the entity is modified
     * @return successful or not
     */
    @Override
    public Boolean update(T entity, User modifiedBy, Date modificationDate) {
        return this.genericMapper.update(entity, modifiedBy, modificationDate) > 0;
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id the primary key of the entity to find
     * @return the entity identified by the primary key, or null if not found
     */
    @Override
    public T findById(PK id) {
        return this.genericMapper.findById(id);
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return a list of all entities
     */
    @Override
    public List<T> findAll() {
        return this.genericMapper.findAll();
    }
}

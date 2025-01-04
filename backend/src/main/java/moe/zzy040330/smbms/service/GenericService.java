/**
 * Package: moe.zzy040330.smbms.service
 * File: GenericService.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 09:50
 * Description: This interface defines the generic CRUD operations to be implemented by all
 * service classes in the application. It provides a unified structure for
 * handling various entity types by utilizing generic parameters for the entity
 * and primary key.
 */
package moe.zzy040330.smbms.service;

import moe.zzy040330.smbms.entity.User;

import java.util.Date;
import java.util.List;

/**
 * @param <T>  the entity type handled by this service
 * @param <PK> the type of the primary key for the entity
 */
public interface GenericService<T, PK> {
    /**
     * Inserts a new entity into the database.
     *
     * @param entity       the entity to be inserted
     * @param createdBy    the user who is creating the entity
     * @param creationDate the date when the entity is created
     * @return successful or not
     */
    Boolean insert(T entity, User createdBy, Date creationDate);

    /**
     * Deletes an entity identified by its primary key from the database.
     *
     * @param id the primary key of the entity to be deleted
     * @return successful or not
     */
    Boolean deleteById(PK id);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity          the entity to be updated
     * @param modifiedBy      the user who is modifying the entity
     * @param modificationDate the date when the entity is modified
     * @return successful or not
     */
    Boolean update(T entity, User modifiedBy, Date modificationDate);

    /**
     * Finds an entity by its primary key.
     *
     * @param id the primary key of the entity to find
     * @return the entity identified by the primary key, or null if not found
     */
    T findById(PK id);

    /**
     * Retrieves all entities from the database.
     *
     * @return a list of all entities
     */
    List<T> findAll();
}

/**
 * Package: moe.zzy040330.smbms.mapper
 * File: GenericMapper.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 01:10
 * Description: This interface defines a generic mapper pattern for basic CRUD
 * operations. It serves as a base interface for entity-specific mappers,
 * allowing for consistent data persistence operations across different
 * entities within the application.
 */

package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * GenericMapper provides a common interface for basic CRUD operations.
 * It uses generic types for flexibility in handling various entity types
 * and primary key types.
 *
 * @param <T>  the type of the entity
 * @param <PK> the type of the primary key
 */
@Mapper
public interface GenericMapper<T, PK> {

    /**
     * Inserts a new record into the database.
     *
     * @param entity the entity to insert
     * @return the number of rows affected
     */
    Integer insert(T entity);

    /**
     * Deletes a record from the database identified by the primary key.
     *
     * @param id the primary key of the entity to delete
     * @return the number of rows affected
     */
    Integer deleteById(PK id);

    /**
     * Updates an existing record in the database.
     *
     * @param entity the entity to update
     * @return the number of rows affected
     */
    Integer update(T entity);

    /**
     * Selects a record from the database by its primary key.
     *
     * @param id the primary key of the entity to select
     * @return the entity matching the primary key, or null if none found
     */
    T findById(PK id);

    /**
     * Selects all records from the database.
     *
     * @return a list of all entities
     */
    List<T> findAll();
}


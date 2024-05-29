package com.company.service;

import java.util.Collection;
import java.util.Set;

/**
 * The {@code CacheService} interface defines the methods for a cache service.
 * This service provides basic operations for saving, retrieving, and managing cached objects.
 *
 * @param <T>  the type of objects to be cached
 * @param <ID> the type of the identifier of the objects
 */
public interface CacheService<T, ID> {

    /**
     * Saves the specified object in the cache.
     *
     * @param object the object to be saved in the cache
     */
    void save(T object);

    /**
     * Saves all the specified objects in the cache.
     *
     * @param objects the collection of objects to be saved in the cache
     */
    void saveAll(Collection<T> objects);

    /**
     * Finds and returns the object with the specified identifier from the cache.
     *
     * @param id the identifier of the object to be retrieved
     * @return the object with the specified identifier, or {@code null} if not found
     */
    T findById(ID id);

    /**
     * Returns a set of all objects currently stored in the cache.
     *
     * @return a set of all cached objects
     */
    Set<T> findAll();
}

package com.company.service;

import com.company.dto.EmployeeStructureNode;

/**
 * The {@code OrganizationCacheService} interface extends {@code CacheService} to provide additional
 * functionality for handling organizational structures.
 *
 * @param <T>  the type of objects to be cached
 * @param <ID> the type of the identifier of the objects
 */
public interface OrganizationCacheService<T, ID> extends CacheService<T, ID> {

    /**
     * Returns the root node of the organizational structure.
     *
     * @return the root {@code EmployeeStructureNode} of the organizational structure
     */
    EmployeeStructureNode getStructure();
}

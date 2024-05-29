package com.company.service;

import java.util.Set;

/**
 * A service interface for reading data from a specified source.
 *
 * @param <T> The type of data to be read.
 */
public interface DataReadService<T> {

    /**
     * Reads data from the specified URI and returns a set of objects of type T.
     *
     * @param uri The URI of the data source.
     * @return A set of objects of type T read from the data source.
     */
    Set<T> readData(String uri);
}

package ca.jrvs.apps.stockquote.dao;

import java.util.Optional;

public interface CrudDao<T, ID> {

    /**
     * Saves a given entity. Used for create and update.
     * @param entity must not be null
     * @return The saved entity. Is never null
     * @throws IllegalArgumentException if id is null
     */
    T save(T entity) throws IllegalArgumentException;

    /**
     * Retrieves an entity by its id.
     * @param id must not be null
     * @return Entity with given id or empty optional if not found
     * @throws IllegalArgumentException if id is null
     */
    Optional<T> findById(ID id) throws IllegalArgumentException;

    /**
     * Retrieves all entities.
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     *  Deletes the entity with the given id. Does nothing if id not found.
     * @param id must not be null
     * @throws IllegalArgumentException if id is null
     */
    void deleteById(ID id) throws IllegalArgumentException;

    /**
     * Deletes all entities in the repository.
     */
    void deleteAll();
}

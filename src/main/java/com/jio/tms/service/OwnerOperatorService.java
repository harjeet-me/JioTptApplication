package com.jio.tms.service;

import com.jio.tms.domain.OwnerOperator;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OwnerOperator}.
 */
public interface OwnerOperatorService {

    /**
     * Save a ownerOperator.
     *
     * @param ownerOperator the entity to save.
     * @return the persisted entity.
     */
    OwnerOperator save(OwnerOperator ownerOperator);

    /**
     * Get all the ownerOperators.
     *
     * @return the list of entities.
     */
    List<OwnerOperator> findAll();


    /**
     * Get the "id" ownerOperator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OwnerOperator> findOne(Long id);

    /**
     * Delete the "id" ownerOperator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the ownerOperator corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<OwnerOperator> search(String query);
}

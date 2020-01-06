package com.jio.tms.service;

import com.jio.tms.domain.TransactionsRecord;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TransactionsRecord}.
 */
public interface TransactionsRecordService {

    /**
     * Save a transactionsRecord.
     *
     * @param transactionsRecord the entity to save.
     * @return the persisted entity.
     */
    TransactionsRecord save(TransactionsRecord transactionsRecord);

    /**
     * Get all the transactionsRecords.
     *
     * @return the list of entities.
     */
    List<TransactionsRecord> findAll();


    /**
     * Get the "id" transactionsRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionsRecord> findOne(Long id);

    /**
     * Delete the "id" transactionsRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the transactionsRecord corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<TransactionsRecord> search(String query);
}

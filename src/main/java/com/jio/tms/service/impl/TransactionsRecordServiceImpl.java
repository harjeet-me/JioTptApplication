package com.jio.tms.service.impl;

import com.jio.tms.service.TransactionsRecordService;
import com.jio.tms.domain.TransactionsRecord;
import com.jio.tms.repository.TransactionsRecordRepository;
import com.jio.tms.repository.search.TransactionsRecordSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TransactionsRecord}.
 */
@Service
@Transactional
public class TransactionsRecordServiceImpl implements TransactionsRecordService {

    private final Logger log = LoggerFactory.getLogger(TransactionsRecordServiceImpl.class);

    private final TransactionsRecordRepository transactionsRecordRepository;

    private final TransactionsRecordSearchRepository transactionsRecordSearchRepository;

    public TransactionsRecordServiceImpl(TransactionsRecordRepository transactionsRecordRepository, TransactionsRecordSearchRepository transactionsRecordSearchRepository) {
        this.transactionsRecordRepository = transactionsRecordRepository;
        this.transactionsRecordSearchRepository = transactionsRecordSearchRepository;
    }

    /**
     * Save a transactionsRecord.
     *
     * @param transactionsRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionsRecord save(TransactionsRecord transactionsRecord) {
        log.debug("Request to save TransactionsRecord : {}", transactionsRecord);
        TransactionsRecord result = transactionsRecordRepository.save(transactionsRecord);
        transactionsRecordSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the transactionsRecords.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionsRecord> findAll() {
        log.debug("Request to get all TransactionsRecords");
        return transactionsRecordRepository.findAll();
    }


    /**
     * Get one transactionsRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionsRecord> findOne(Long id) {
        log.debug("Request to get TransactionsRecord : {}", id);
        return transactionsRecordRepository.findById(id);
    }

    /**
     * Delete the transactionsRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionsRecord : {}", id);
        transactionsRecordRepository.deleteById(id);
        transactionsRecordSearchRepository.deleteById(id);
    }

    /**
     * Search for the transactionsRecord corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionsRecord> search(String query) {
        log.debug("Request to search TransactionsRecords for query {}", query);
        return StreamSupport
            .stream(transactionsRecordSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

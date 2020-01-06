package com.jio.tms.service.impl;

import com.jio.tms.service.OwnerOperatorService;
import com.jio.tms.domain.OwnerOperator;
import com.jio.tms.repository.OwnerOperatorRepository;
import com.jio.tms.repository.search.OwnerOperatorSearchRepository;
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
 * Service Implementation for managing {@link OwnerOperator}.
 */
@Service
@Transactional
public class OwnerOperatorServiceImpl implements OwnerOperatorService {

    private final Logger log = LoggerFactory.getLogger(OwnerOperatorServiceImpl.class);

    private final OwnerOperatorRepository ownerOperatorRepository;

    private final OwnerOperatorSearchRepository ownerOperatorSearchRepository;

    public OwnerOperatorServiceImpl(OwnerOperatorRepository ownerOperatorRepository, OwnerOperatorSearchRepository ownerOperatorSearchRepository) {
        this.ownerOperatorRepository = ownerOperatorRepository;
        this.ownerOperatorSearchRepository = ownerOperatorSearchRepository;
    }

    /**
     * Save a ownerOperator.
     *
     * @param ownerOperator the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OwnerOperator save(OwnerOperator ownerOperator) {
        log.debug("Request to save OwnerOperator : {}", ownerOperator);
        OwnerOperator result = ownerOperatorRepository.save(ownerOperator);
        ownerOperatorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ownerOperators.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OwnerOperator> findAll() {
        log.debug("Request to get all OwnerOperators");
        return ownerOperatorRepository.findAll();
    }


    /**
     * Get one ownerOperator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OwnerOperator> findOne(Long id) {
        log.debug("Request to get OwnerOperator : {}", id);
        return ownerOperatorRepository.findById(id);
    }

    /**
     * Delete the ownerOperator by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OwnerOperator : {}", id);
        ownerOperatorRepository.deleteById(id);
        ownerOperatorSearchRepository.deleteById(id);
    }

    /**
     * Search for the ownerOperator corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OwnerOperator> search(String query) {
        log.debug("Request to search OwnerOperators for query {}", query);
        return StreamSupport
            .stream(ownerOperatorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

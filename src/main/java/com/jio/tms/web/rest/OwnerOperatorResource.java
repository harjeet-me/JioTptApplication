package com.jio.tms.web.rest;

import com.jio.tms.domain.OwnerOperator;
import com.jio.tms.service.OwnerOperatorService;
import com.jio.tms.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.jio.tms.domain.OwnerOperator}.
 */
@RestController
@RequestMapping("/api")
public class OwnerOperatorResource {

    private final Logger log = LoggerFactory.getLogger(OwnerOperatorResource.class);

    private static final String ENTITY_NAME = "ownerOperator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OwnerOperatorService ownerOperatorService;

    public OwnerOperatorResource(OwnerOperatorService ownerOperatorService) {
        this.ownerOperatorService = ownerOperatorService;
    }

    /**
     * {@code POST  /owner-operators} : Create a new ownerOperator.
     *
     * @param ownerOperator the ownerOperator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ownerOperator, or with status {@code 400 (Bad Request)} if the ownerOperator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/owner-operators")
    public ResponseEntity<OwnerOperator> createOwnerOperator(@RequestBody OwnerOperator ownerOperator) throws URISyntaxException {
        log.debug("REST request to save OwnerOperator : {}", ownerOperator);
        if (ownerOperator.getId() != null) {
            throw new BadRequestAlertException("A new ownerOperator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnerOperator result = ownerOperatorService.save(ownerOperator);
        return ResponseEntity.created(new URI("/api/owner-operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /owner-operators} : Updates an existing ownerOperator.
     *
     * @param ownerOperator the ownerOperator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ownerOperator,
     * or with status {@code 400 (Bad Request)} if the ownerOperator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ownerOperator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/owner-operators")
    public ResponseEntity<OwnerOperator> updateOwnerOperator(@RequestBody OwnerOperator ownerOperator) throws URISyntaxException {
        log.debug("REST request to update OwnerOperator : {}", ownerOperator);
        if (ownerOperator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OwnerOperator result = ownerOperatorService.save(ownerOperator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ownerOperator.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /owner-operators} : get all the ownerOperators.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ownerOperators in body.
     */
    @GetMapping("/owner-operators")
    public List<OwnerOperator> getAllOwnerOperators() {
        log.debug("REST request to get all OwnerOperators");
        return ownerOperatorService.findAll();
    }

    /**
     * {@code GET  /owner-operators/:id} : get the "id" ownerOperator.
     *
     * @param id the id of the ownerOperator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ownerOperator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/owner-operators/{id}")
    public ResponseEntity<OwnerOperator> getOwnerOperator(@PathVariable Long id) {
        log.debug("REST request to get OwnerOperator : {}", id);
        Optional<OwnerOperator> ownerOperator = ownerOperatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ownerOperator);
    }

    /**
     * {@code DELETE  /owner-operators/:id} : delete the "id" ownerOperator.
     *
     * @param id the id of the ownerOperator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/owner-operators/{id}")
    public ResponseEntity<Void> deleteOwnerOperator(@PathVariable Long id) {
        log.debug("REST request to delete OwnerOperator : {}", id);
        ownerOperatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/owner-operators?query=:query} : search for the ownerOperator corresponding
     * to the query.
     *
     * @param query the query of the ownerOperator search.
     * @return the result of the search.
     */
    @GetMapping("/_search/owner-operators")
    public List<OwnerOperator> searchOwnerOperators(@RequestParam String query) {
        log.debug("REST request to search OwnerOperators for query {}", query);
        return ownerOperatorService.search(query);
    }
}

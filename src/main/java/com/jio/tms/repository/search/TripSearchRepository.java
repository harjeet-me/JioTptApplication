package com.jio.tms.repository.search;

import com.jio.tms.domain.Trip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Trip} entity.
 */
public interface TripSearchRepository extends ElasticsearchRepository<Trip, Long> {
}

package com.jio.tms.repository.search;

import com.jio.tms.domain.Driver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Driver} entity.
 */
public interface DriverSearchRepository extends ElasticsearchRepository<Driver, Long> {
}

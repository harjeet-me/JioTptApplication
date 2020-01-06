package com.jio.tms.repository.search;

import com.jio.tms.domain.Insurance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Insurance} entity.
 */
public interface InsuranceSearchRepository extends ElasticsearchRepository<Insurance, Long> {
}

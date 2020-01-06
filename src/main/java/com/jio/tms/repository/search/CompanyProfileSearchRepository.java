package com.jio.tms.repository.search;

import com.jio.tms.domain.CompanyProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompanyProfile} entity.
 */
public interface CompanyProfileSearchRepository extends ElasticsearchRepository<CompanyProfile, Long> {
}

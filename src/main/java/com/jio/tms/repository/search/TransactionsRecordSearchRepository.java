package com.jio.tms.repository.search;

import com.jio.tms.domain.TransactionsRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransactionsRecord} entity.
 */
public interface TransactionsRecordSearchRepository extends ElasticsearchRepository<TransactionsRecord, Long> {
}

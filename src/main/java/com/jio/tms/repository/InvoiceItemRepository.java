package com.jio.tms.repository;

import com.jio.tms.domain.InvoiceItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvoiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

}

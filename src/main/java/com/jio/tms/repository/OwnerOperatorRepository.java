package com.jio.tms.repository;

import com.jio.tms.domain.OwnerOperator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OwnerOperator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerOperatorRepository extends JpaRepository<OwnerOperator, Long> {

}

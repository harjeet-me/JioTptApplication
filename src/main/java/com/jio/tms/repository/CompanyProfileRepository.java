package com.jio.tms.repository;

import com.jio.tms.domain.CompanyProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompanyProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

}

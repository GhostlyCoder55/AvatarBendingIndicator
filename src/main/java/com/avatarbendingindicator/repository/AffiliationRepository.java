package com.avatarbendingindicator.repository;

import com.avatarbendingindicator.model.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    boolean existsByNameIgnoreCase(String name);
}

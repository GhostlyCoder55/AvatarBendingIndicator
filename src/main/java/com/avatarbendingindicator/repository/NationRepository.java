package com.avatarbendingindicator.repository;

import com.avatarbendingindicator.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Long> {
    boolean existsByNameIgnoreCase(String name);
}

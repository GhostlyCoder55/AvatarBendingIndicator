package com.avatarbendingindicator.repository;

import com.avatarbendingindicator.model.BendingStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BendingStyleRepository extends JpaRepository<BendingStyle, Long> {
    boolean existsByNameIgnoreCase(String name);
}

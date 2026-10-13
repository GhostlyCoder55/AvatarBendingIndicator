package com.avatarbendingindicator.repository;

import com.avatarbendingindicator.model.AvatarCharacter;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CharacterRepository extends JpaRepository<AvatarCharacter, Long>, JpaSpecificationExecutor<AvatarCharacter> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @EntityGraph(attributePaths = {"nation", "bendingStyles", "affiliations", "aliases"})
    Optional<AvatarCharacter> findWithDetailsById(Long id);

    @EntityGraph(attributePaths = {"nation", "bendingStyles", "affiliations", "aliases"})
    Optional<AvatarCharacter> findByNameIgnoreCase(String name);

    @Query("""
            SELECT DISTINCT c FROM AvatarCharacter c
            LEFT JOIN c.aliases alias
            WHERE LOWER(alias) = LOWER(:name)
            """)
    Optional<AvatarCharacter> findByAliasIgnoreCase(@Param("name") String name);
}

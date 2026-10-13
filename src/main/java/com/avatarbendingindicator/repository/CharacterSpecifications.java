package com.avatarbendingindicator.repository;

import com.avatarbendingindicator.model.AvatarCharacter;
import com.avatarbendingindicator.model.CharacterType;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class CharacterSpecifications {

    private CharacterSpecifications() {
    }

    public static Specification<AvatarCharacter> withFilters(
            String name,
            Long nationId,
            Long bendingStyleId,
            Long affiliationId,
            Boolean avatar,
            CharacterType type
    ) {
        return Specification
                .where(nameContains(name))
                .and(hasNation(nationId))
                .and(hasBendingStyle(bendingStyleId))
                .and(hasAffiliation(affiliationId))
                .and(isAvatar(avatar))
                .and(hasType(type));
    }

    private static Specification<AvatarCharacter> nameContains(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        String like = "%" + name.toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), like);
    }

    private static Specification<AvatarCharacter> hasNation(Long nationId) {
        if (nationId == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("nation").get("id"), nationId);
    }

    private static Specification<AvatarCharacter> hasBendingStyle(Long bendingStyleId) {
        if (bendingStyleId == null) {
            return null;
        }
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.join("bendingStyles", JoinType.INNER).get("id"), bendingStyleId);
        };
    }

    private static Specification<AvatarCharacter> hasAffiliation(Long affiliationId) {
        if (affiliationId == null) {
            return null;
        }
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.join("affiliations", JoinType.INNER).get("id"), affiliationId);
        };
    }

    private static Specification<AvatarCharacter> isAvatar(Boolean avatar) {
        if (avatar == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("avatar"), avatar);
    }

    private static Specification<AvatarCharacter> hasType(CharacterType type) {
        if (type == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("characterType"), type);
    }
}

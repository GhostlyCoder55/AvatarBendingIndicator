package com.avatarbendingindicator.mapper;

import com.avatarbendingindicator.dto.response.CharacterResponse;
import com.avatarbendingindicator.dto.response.NamedResourceResponse;
import com.avatarbendingindicator.model.Affiliation;
import com.avatarbendingindicator.model.AvatarCharacter;
import com.avatarbendingindicator.model.BendingStyle;
import com.avatarbendingindicator.model.Nation;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    public CharacterResponse toResponse(AvatarCharacter character) {
        return new CharacterResponse(
                character.getId(),
                character.getName(),
                character.getSummary(),
                character.getBiography(),
                character.getCharacterType(),
                character.isAvatar(),
                toNation(character.getNation()),
                character.getBendingStyles().stream().map(this::toBending).collect(Collectors.toSet()),
                character.getAffiliations().stream().map(this::toAffiliation).collect(Collectors.toSet()),
                Set.copyOf(character.getAliases())
        );
    }

    public NamedResourceResponse toNation(Nation nation) {
        if (nation == null) {
            return null;
        }
        return new NamedResourceResponse(nation.getId(), nation.getName(), nation.getElement(), nation.getDescription());
    }

    public NamedResourceResponse toBending(BendingStyle style) {
        return new NamedResourceResponse(style.getId(), style.getName(), null, style.getDescription());
    }

    public NamedResourceResponse toAffiliation(Affiliation affiliation) {
        return new NamedResourceResponse(affiliation.getId(), affiliation.getName(), null, affiliation.getDescription());
    }
}

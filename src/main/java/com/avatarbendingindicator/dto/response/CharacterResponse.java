package com.avatarbendingindicator.dto.response;

import com.avatarbendingindicator.model.CharacterType;
import java.util.Set;

public record CharacterResponse(
        Long id,
        String name,
        String summary,
        String biography,
        CharacterType characterType,
        boolean avatar,
        NamedResourceResponse nation,
        Set<NamedResourceResponse> bendingStyles,
        Set<NamedResourceResponse> affiliations,
        Set<String> aliases
) {
}

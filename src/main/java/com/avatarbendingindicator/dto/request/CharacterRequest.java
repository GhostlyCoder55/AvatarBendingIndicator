package com.avatarbendingindicator.dto.request;

import com.avatarbendingindicator.model.CharacterType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CharacterRequest(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 255) String summary,
        @Size(max = 2000) String biography,
        @NotNull CharacterType characterType,
        boolean avatar,
        Long nationId,
        Set<Long> bendingStyleIds,
        Set<Long> affiliationIds,
        Set<@Size(max = 150) String> aliases
) {
}

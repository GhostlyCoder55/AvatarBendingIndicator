package com.avatarbendingindicator.lookup;

import com.avatarbendingindicator.model.AvatarCharacter;
import com.avatarbendingindicator.repository.CharacterRepository;
import java.util.Optional;

public class AliasLookupHandler extends CharacterLookupHandler {

    private final CharacterRepository characterRepository;

    public AliasLookupHandler(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    protected Optional<AvatarCharacter> doLookup(String name) {
        return characterRepository.findByAliasIgnoreCase(name)
                .flatMap(character -> characterRepository.findWithDetailsById(character.getId()));
    }
}

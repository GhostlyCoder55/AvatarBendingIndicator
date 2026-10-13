package com.avatarbendingindicator.lookup;

import com.avatarbendingindicator.model.AvatarCharacter;
import com.avatarbendingindicator.repository.CharacterRepository;
import java.util.Optional;

public class NameLookupHandler extends CharacterLookupHandler {

    private final CharacterRepository characterRepository;

    public NameLookupHandler(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    protected Optional<AvatarCharacter> doLookup(String name) {
        return characterRepository.findByNameIgnoreCase(name);
    }
}

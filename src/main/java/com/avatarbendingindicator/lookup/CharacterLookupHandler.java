package com.avatarbendingindicator.lookup;

import com.avatarbendingindicator.model.AvatarCharacter;
import java.util.Optional;

public abstract class CharacterLookupHandler {

    private CharacterLookupHandler next;

    public CharacterLookupHandler linkWith(CharacterLookupHandler next) {
        this.next = next;
        return next;
    }

    public Optional<AvatarCharacter> handle(String name) {
        Optional<AvatarCharacter> match = doLookup(name);
        if (match.isPresent()) {
            return match;
        }
        return next == null ? Optional.empty() : next.handle(name);
    }

    protected abstract Optional<AvatarCharacter> doLookup(String name);
}

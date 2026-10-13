package com.avatarbendingindicator.lookup;

import com.avatarbendingindicator.repository.CharacterRepository;
import org.springframework.stereotype.Component;

@Component
public class CharacterLookupChainFactory {

    private final CharacterRepository characterRepository;

    public CharacterLookupChainFactory(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public CharacterLookupHandler createChain() {
        CharacterLookupHandler nameLookup = new NameLookupHandler(characterRepository);
        nameLookup.linkWith(new AliasLookupHandler(characterRepository));
        return nameLookup;
    }
}

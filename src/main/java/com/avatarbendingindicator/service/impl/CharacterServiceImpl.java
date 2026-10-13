package com.avatarbendingindicator.service.impl;

import com.avatarbendingindicator.config.CacheConfig;
import com.avatarbendingindicator.dto.request.CharacterRequest;
import com.avatarbendingindicator.dto.response.CharacterLookupResponse;
import com.avatarbendingindicator.dto.response.CharacterResponse;
import com.avatarbendingindicator.dto.response.PagedResponse;
import com.avatarbendingindicator.exception.DuplicateResourceException;
import com.avatarbendingindicator.exception.ResourceNotFoundException;
import com.avatarbendingindicator.lookup.CharacterLookupChainFactory;
import com.avatarbendingindicator.mapper.CharacterMapper;
import com.avatarbendingindicator.model.Affiliation;
import com.avatarbendingindicator.model.AvatarCharacter;
import com.avatarbendingindicator.model.BendingStyle;
import com.avatarbendingindicator.model.CharacterType;
import com.avatarbendingindicator.model.Nation;
import com.avatarbendingindicator.repository.AffiliationRepository;
import com.avatarbendingindicator.repository.BendingStyleRepository;
import com.avatarbendingindicator.repository.CharacterRepository;
import com.avatarbendingindicator.repository.CharacterSpecifications;
import com.avatarbendingindicator.repository.NationRepository;
import com.avatarbendingindicator.service.CharacterService;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class CharacterServiceImpl implements CharacterService {

    private static final Logger log = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository characterRepository;
    private final NationRepository nationRepository;
    private final BendingStyleRepository bendingStyleRepository;
    private final AffiliationRepository affiliationRepository;
    private final CharacterMapper characterMapper;
    private final CharacterLookupChainFactory lookupChainFactory;

    public CharacterServiceImpl(
            CharacterRepository characterRepository,
            NationRepository nationRepository,
            BendingStyleRepository bendingStyleRepository,
            AffiliationRepository affiliationRepository,
            CharacterMapper characterMapper,
            CharacterLookupChainFactory lookupChainFactory
    ) {
        this.characterRepository = characterRepository;
        this.nationRepository = nationRepository;
        this.bendingStyleRepository = bendingStyleRepository;
        this.affiliationRepository = affiliationRepository;
        this.characterMapper = characterMapper;
        this.lookupChainFactory = lookupChainFactory;
    }

    @Override
    @Cacheable(cacheNames = CacheConfig.CHARACTER_LOOKUPS, key = "#name.toLowerCase()")
    public CharacterLookupResponse lookup(String name) {
        String query = name == null ? "" : name.trim();
        if (!StringUtils.hasText(query)) {
            return CharacterLookupResponse.missing(query);
        }
        return lookupChainFactory.createChain()
                .handle(query)
                .map(character -> CharacterLookupResponse.found(query, character.getName(), character.getSummary()))
                .orElseGet(() -> CharacterLookupResponse.missing(query));
    }

    @Override
    public PagedResponse<CharacterResponse> search(
            String name,
            Long nationId,
            Long bendingStyleId,
            Long affiliationId,
            Boolean avatar,
            CharacterType type,
            Pageable pageable
    ) {
        return PagedResponse.from(characterRepository
                .findAll(CharacterSpecifications.withFilters(name, nationId, bendingStyleId, affiliationId, avatar, type), pageable)
                .map(characterMapper::toResponse));
    }

    @Override
    @Cacheable(cacheNames = CacheConfig.CHARACTERS, key = "#id")
    public CharacterResponse getById(Long id) {
        AvatarCharacter character = characterRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found: " + id));
        return characterMapper.toResponse(character);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConfig.CHARACTERS, allEntries = true),
            @CacheEvict(cacheNames = CacheConfig.CHARACTER_LOOKUPS, allEntries = true)
    })
    public CharacterResponse create(CharacterRequest request) {
        if (characterRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Character already exists: " + request.name());
        }
        AvatarCharacter character = new AvatarCharacter();
        apply(character, request);
        AvatarCharacter saved = characterRepository.save(character);
        log.info("Created character {}", saved.getName());
        return characterMapper.toResponse(characterRepository.findWithDetailsById(saved.getId()).orElse(saved));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConfig.CHARACTERS, allEntries = true),
            @CacheEvict(cacheNames = CacheConfig.CHARACTER_LOOKUPS, allEntries = true)
    })
    public CharacterResponse update(Long id, CharacterRequest request) {
        AvatarCharacter character = characterRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found: " + id));
        if (characterRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new DuplicateResourceException("Character already exists: " + request.name());
        }
        apply(character, request);
        log.info("Updated character {}", id);
        return characterMapper.toResponse(character);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConfig.CHARACTERS, allEntries = true),
            @CacheEvict(cacheNames = CacheConfig.CHARACTER_LOOKUPS, allEntries = true)
    })
    public void delete(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Character not found: " + id);
        }
        characterRepository.deleteById(id);
        log.info("Deleted character {}", id);
    }

    private void apply(AvatarCharacter character, CharacterRequest request) {
        character.setName(request.name().trim());
        character.setSummary(request.summary().trim());
        character.setBiography(request.biography());
        character.setCharacterType(request.characterType());
        character.setAvatar(request.avatar());
        character.setNation(resolveNation(request.nationId()));
        character.setBendingStyles(resolveBendingStyles(request.bendingStyleIds()));
        character.setAffiliations(resolveAffiliations(request.affiliationIds()));
        character.setAliases(request.aliases() == null ? new HashSet<>() : new HashSet<>(request.aliases()));
    }

    private Nation resolveNation(Long nationId) {
        if (nationId == null) {
            return null;
        }
        return nationRepository.findById(nationId)
                .orElseThrow(() -> new ResourceNotFoundException("Nation not found: " + nationId));
    }

    private Set<BendingStyle> resolveBendingStyles(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        Set<BendingStyle> styles = new HashSet<>(bendingStyleRepository.findAllById(ids));
        if (styles.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more bending styles were not found.");
        }
        return styles;
    }

    private Set<Affiliation> resolveAffiliations(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        Set<Affiliation> affiliations = new HashSet<>(affiliationRepository.findAllById(ids));
        if (affiliations.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more affiliations were not found.");
        }
        return affiliations;
    }
}

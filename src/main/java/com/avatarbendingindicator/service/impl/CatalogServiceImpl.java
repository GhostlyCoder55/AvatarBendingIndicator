package com.avatarbendingindicator.service.impl;

import com.avatarbendingindicator.config.CacheConfig;
import com.avatarbendingindicator.dto.request.NamedResourceRequest;
import com.avatarbendingindicator.dto.response.NamedResourceResponse;
import com.avatarbendingindicator.exception.DuplicateResourceException;
import com.avatarbendingindicator.exception.ResourceNotFoundException;
import com.avatarbendingindicator.model.Affiliation;
import com.avatarbendingindicator.model.BendingStyle;
import com.avatarbendingindicator.model.Nation;
import com.avatarbendingindicator.repository.AffiliationRepository;
import com.avatarbendingindicator.repository.BendingStyleRepository;
import com.avatarbendingindicator.repository.NationRepository;
import com.avatarbendingindicator.service.CatalogService;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

    private final NationRepository nationRepository;
    private final BendingStyleRepository bendingStyleRepository;
    private final AffiliationRepository affiliationRepository;

    public CatalogServiceImpl(
            NationRepository nationRepository,
            BendingStyleRepository bendingStyleRepository,
            AffiliationRepository affiliationRepository
    ) {
        this.nationRepository = nationRepository;
        this.bendingStyleRepository = bendingStyleRepository;
        this.affiliationRepository = affiliationRepository;
    }

    @Override
    @Cacheable(CacheConfig.NATIONS)
    public List<NamedResourceResponse> listNations() {
        return nationRepository.findAll().stream().map(this::toNation).toList();
    }

    @Override
    public NamedResourceResponse getNation(Long id) {
        return nationRepository.findById(id)
                .map(this::toNation)
                .orElseThrow(() -> new ResourceNotFoundException("Nation not found: " + id));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheConfig.NATIONS, allEntries = true)
    public NamedResourceResponse createNation(NamedResourceRequest request) {
        if (nationRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Nation already exists: " + request.name());
        }
        Nation nation = new Nation();
        nation.setName(request.name());
        nation.setElement(request.element() == null ? "Unknown" : request.element());
        nation.setDescription(request.description());
        return toNation(nationRepository.save(nation));
    }

    @Override
    @Cacheable(CacheConfig.BENDING_STYLES)
    public List<NamedResourceResponse> listBendingStyles() {
        return bendingStyleRepository.findAll().stream().map(this::toBending).toList();
    }

    @Override
    public NamedResourceResponse getBendingStyle(Long id) {
        return bendingStyleRepository.findById(id)
                .map(this::toBending)
                .orElseThrow(() -> new ResourceNotFoundException("Bending style not found: " + id));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheConfig.BENDING_STYLES, allEntries = true)
    public NamedResourceResponse createBendingStyle(NamedResourceRequest request) {
        if (bendingStyleRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Bending style already exists: " + request.name());
        }
        BendingStyle style = new BendingStyle();
        style.setName(request.name());
        style.setDescription(request.description());
        return toBending(bendingStyleRepository.save(style));
    }

    @Override
    @Cacheable(CacheConfig.AFFILIATIONS)
    public List<NamedResourceResponse> listAffiliations() {
        return affiliationRepository.findAll().stream().map(this::toAffiliation).toList();
    }

    @Override
    public NamedResourceResponse getAffiliation(Long id) {
        return affiliationRepository.findById(id)
                .map(this::toAffiliation)
                .orElseThrow(() -> new ResourceNotFoundException("Affiliation not found: " + id));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheConfig.AFFILIATIONS, allEntries = true)
    public NamedResourceResponse createAffiliation(NamedResourceRequest request) {
        if (affiliationRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Affiliation already exists: " + request.name());
        }
        Affiliation affiliation = new Affiliation();
        affiliation.setName(request.name());
        affiliation.setDescription(request.description());
        return toAffiliation(affiliationRepository.save(affiliation));
    }

    private NamedResourceResponse toNation(Nation nation) {
        return new NamedResourceResponse(nation.getId(), nation.getName(), nation.getElement(), nation.getDescription());
    }

    private NamedResourceResponse toBending(BendingStyle style) {
        return new NamedResourceResponse(style.getId(), style.getName(), null, style.getDescription());
    }

    private NamedResourceResponse toAffiliation(Affiliation affiliation) {
        return new NamedResourceResponse(
                affiliation.getId(), affiliation.getName(), null, affiliation.getDescription());
    }
}

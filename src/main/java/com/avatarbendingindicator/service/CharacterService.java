package com.avatarbendingindicator.service;

import com.avatarbendingindicator.dto.request.CharacterRequest;
import com.avatarbendingindicator.dto.response.CharacterLookupResponse;
import com.avatarbendingindicator.dto.response.CharacterResponse;
import com.avatarbendingindicator.dto.response.PagedResponse;
import com.avatarbendingindicator.model.CharacterType;
import org.springframework.data.domain.Pageable;

public interface CharacterService {

    CharacterLookupResponse lookup(String name);

    PagedResponse<CharacterResponse> search(
            String name,
            Long nationId,
            Long bendingStyleId,
            Long affiliationId,
            Boolean avatar,
            CharacterType type,
            Pageable pageable
    );

    CharacterResponse getById(Long id);

    CharacterResponse create(CharacterRequest request);

    CharacterResponse update(Long id, CharacterRequest request);

    void delete(Long id);
}

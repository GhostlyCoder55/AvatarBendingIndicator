package com.avatarbendingindicator.dto.response;

public record NamedResourceResponse(
        Long id,
        String name,
        String element,
        String description
) {
}

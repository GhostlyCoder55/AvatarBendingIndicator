package com.avatarbendingindicator.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NamedResourceRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 50) String element,
        @Size(max = 500) String description
) {
}

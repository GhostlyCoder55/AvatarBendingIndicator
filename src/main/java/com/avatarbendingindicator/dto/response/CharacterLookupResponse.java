package com.avatarbendingindicator.dto.response;

public record CharacterLookupResponse(
        boolean found,
        String query,
        String name,
        String summary,
        String message
) {
    public static CharacterLookupResponse found(String query, String name, String summary) {
        return new CharacterLookupResponse(true, query, name, summary, name + " is: " + summary);
    }

    public static CharacterLookupResponse missing(String query) {
        return new CharacterLookupResponse(
                false,
                query,
                null,
                null,
                query + " is not in \"Avatar the Last AirBender\""
        );
    }
}

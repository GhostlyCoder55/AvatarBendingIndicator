package com.avatarbendingindicator.service;

import com.avatarbendingindicator.dto.request.NamedResourceRequest;
import com.avatarbendingindicator.dto.response.NamedResourceResponse;
import java.util.List;

public interface CatalogService {
    List<NamedResourceResponse> listNations();

    NamedResourceResponse getNation(Long id);

    NamedResourceResponse createNation(NamedResourceRequest request);

    List<NamedResourceResponse> listBendingStyles();

    NamedResourceResponse getBendingStyle(Long id);

    NamedResourceResponse createBendingStyle(NamedResourceRequest request);

    List<NamedResourceResponse> listAffiliations();

    NamedResourceResponse getAffiliation(Long id);

    NamedResourceResponse createAffiliation(NamedResourceRequest request);
}

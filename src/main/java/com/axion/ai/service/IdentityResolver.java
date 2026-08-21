package com.axion.ai.service;

import com.axion.authentication.entity.User;
import com.axion.ai.dto.DocumentAnalysisResponse;
import com.axion.ai.dto.IdentityMatchResult;

public interface IdentityResolver {
    IdentityMatchResult resolve(User user, DocumentAnalysisResponse document);
}

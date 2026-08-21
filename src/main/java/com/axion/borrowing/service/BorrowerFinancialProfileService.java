package com.axion.borrowing.service;
import java.util.UUID;
import com.axion.borrowing.dto.CreateFinancialProfileRequest;
import com.axion.borrowing.dto.FinancialProfileResponse;
public interface BorrowerFinancialProfileService { FinancialProfileResponse createOrUpdateProfile(UUID borrowerId, CreateFinancialProfileRequest request); FinancialProfileResponse getProfile(UUID borrowerId); }

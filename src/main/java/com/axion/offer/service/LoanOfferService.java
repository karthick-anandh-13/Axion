package com.axion.offer.service;

import java.util.List;
import java.util.UUID;

import com.axion.offer.dto.LoanOfferResponse;

public interface LoanOfferService {

    List<LoanOfferResponse> generateOffers(UUID borrowingRequestId);

    List<LoanOfferResponse> getOffers(UUID borrowingRequestId);

}
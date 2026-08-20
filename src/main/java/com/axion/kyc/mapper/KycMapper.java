package com.axion.kyc.mapper;

import com.axion.kyc.dto.KycResponse;
import com.axion.kyc.entity.KycVerification;

public final class KycMapper {

    private KycMapper() {
        // Prevent instantiation
    }

    public static KycResponse toResponse(
            KycVerification kyc) {

        KycResponse response = new KycResponse();

        response.setId(kyc.getId());

        if (kyc.getCustomer() != null) {
            response.setCustomerId(
                    kyc.getCustomer().getId()
            );
        }

        response.setStatus(kyc.getStatus());
        response.setVerificationReference(
                kyc.getVerificationReference()
        );
        response.setRejectionReason(
                kyc.getRejectionReason()
        );
        response.setSubmittedAt(
                kyc.getSubmittedAt()
        );
        response.setVerifiedAt(
                kyc.getVerifiedAt()
        );
        response.setExpiresAt(
                kyc.getExpiresAt()
        );
        response.setCreatedAt(
                kyc.getCreatedAt()
        );
        response.setUpdatedAt(
                kyc.getUpdatedAt()
        );

        return response;
    }
}
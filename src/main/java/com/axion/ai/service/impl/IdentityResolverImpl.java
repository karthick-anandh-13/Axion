package com.axion.ai.service.impl;

import org.springframework.stereotype.Service;

import com.axion.ai.dto.DocumentAnalysisResponse;
import com.axion.ai.dto.IdentityMatchResult;
import com.axion.ai.service.IdentityResolver;
import com.axion.authentication.entity.User;

@Service
public class IdentityResolverImpl
        implements IdentityResolver {

    @Override
    public IdentityMatchResult resolve(
            User user,
            DocumentAnalysisResponse document) {

        String userName =
                normalize(
                        user.getFirstName()
                                + " "
                                + user.getLastName()
                );

        String documentName =
                normalize(
                        document.name().value()
                );

        double nameSimilarity =
                calculateNameSimilarity(
                        userName,
                        documentName
                );

        boolean nameMatch =
                nameSimilarity >= 0.90;

        /*
         * DOB and document-number comparison
         * will be connected when the corresponding
         * customer/document fields are available.
         */

        boolean dateOfBirthMatch = false;

        boolean documentNumberMatch = false;

        boolean matched =
                nameMatch;

        double confidence =
                calculateConfidence(
                        nameSimilarity,
                        dateOfBirthMatch,
                        documentNumberMatch
                );

        return new IdentityMatchResult(
                matched,
                confidence,
                nameSimilarity,
                dateOfBirthMatch,
                documentNumberMatch,
                buildReason(
                        nameSimilarity,
                        matched
                )
        );
    }

    private String normalize(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .toUpperCase()
                .replaceAll("[^A-Z0-9 ]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private double calculateNameSimilarity(
            String first,
            String second) {

        if (first.isEmpty()
                || second.isEmpty()) {

            return 0.0;
        }

        if (first.equals(second)) {
            return 1.0;
        }

        return 0.0;
    }

    private double calculateConfidence(
            double nameSimilarity,
            boolean dateOfBirthMatch,
            boolean documentNumberMatch) {

        double score =
                nameSimilarity * 0.60;

        score +=
                dateOfBirthMatch
                        ? 0.20
                        : 0.0;

        score +=
                documentNumberMatch
                        ? 0.20
                        : 0.0;

        return Math.min(
                score,
                1.0
        );
    }

    private String buildReason(
            double similarity,
            boolean matched) {

        if (matched) {
            return "Identity information matched.";
        }

        return "Identity information did not sufficiently match.";
    }
}
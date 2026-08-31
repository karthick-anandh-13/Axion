package com.axion.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditInsightResponse {

    private int creditScore;
    private String rating;
    private String riskLevel;

    private double repaymentProbability;
    private double defaultRisk;

    private String insight;
    private String recommendation;
}
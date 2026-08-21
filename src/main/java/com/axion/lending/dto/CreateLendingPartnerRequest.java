package com.axion.lending.dto;
import java.math.BigDecimal;
import com.axion.lending.entity.RiskPreference;
import jakarta.validation.constraints.*;
public record CreateLendingPartnerRequest(@NotBlank String organizationName, @NotNull BigDecimal totalCapital, @NotNull BigDecimal minimumApr, @NotNull BigDecimal maximumApr, @NotNull RiskPreference riskPreference) {}

package com.brief.citronix.dto;

import com.brief.citronix.enums.Season;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for creating a new Farm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestCreateDTO {

    private UUID id;

    @PastOrPresent(message = "Harvest date must be in the past or present")
    @NotNull(message = "Harvest date is required")
    private LocalDateTime harvestDate;

    private double totalQuantity;

    @NotNull(message = "Season is required")
    @Enumerated(EnumType.STRING)
    private Season season;

    List<SaleCreateDTO> sales;
}

package com.brief.citronix.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for creating a new Farm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateDTO {

    private UUID id;

    @NotNull(message = "Sale date is required")
    @PastOrPresent(message = "Sale date must be in the past or present")
    private LocalDateTime saleDate;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be greater than 0")
    private double unitPrice;

    @NotNull(message = "Harvest ID is required")
    private UUID harvestId;

    @NotNull(message = "Client name is required")
    @NotBlank(message = "Client name cannot be blank")
    private String clientName;


}

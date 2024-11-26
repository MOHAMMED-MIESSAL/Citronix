package com.brief.citronix.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailCreateDTO {

    private UUID id;
    @NotNull(message = "Quantity is required")
    @NotBlank(message = "Quantity is required")
    private double quantity;
    @NotNull(message = "Harvest ID is required")
    private UUID harvestId;
    @NotNull(message = "Tree ID is required")
    private UUID treeId;

}

package com.brief.citronix.dto;



import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Tree;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * DTO for creating a new Farm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldCreateDTO {

    private UUID id;

    @Positive(message = "Area must be greater than 0")
    @NotNull(message = "Area cannot be null")
    @DecimalMin(value = "0.1", message = "Field area must be at least 0.1 hectares (1,000 m²).")
    private double area;

    @NotNull(message = "Farm ID cannot be null")
    @JsonProperty("farm_id")
    private UUID farmId;

    private FarmCreateDTO farm;

    private List<TreeCreateDTO> trees;
}

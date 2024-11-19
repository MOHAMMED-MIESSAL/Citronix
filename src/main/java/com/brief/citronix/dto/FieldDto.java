package com.brief.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.brief.citronix.domain.Farm}
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FieldDto implements Serializable {

    @DecimalMin(value = "1000", message = "The area must be at least 1000 m² (0.1 hectare).")
    @Positive(message = "The area must be a positive value.")
    private double area;

    @NotNull(message = "Farm ID cannot be null.")
    private UUID farm_id;




}

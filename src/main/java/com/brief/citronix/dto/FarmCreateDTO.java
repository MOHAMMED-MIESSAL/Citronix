package com.brief.citronix.dto;

import com.brief.citronix.domain.Field;
import lombok.*;
import jakarta.validation.constraints.*;

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
public class FarmCreateDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Location cannot be blank")
    @NotNull(message = "Location cannot be null")
    private String location;

    @Positive(message = "Area must be greater than 0")
    @Min(value = 1, message = "Area must be at least 1 hectare (10,000 m²)")
    private double area;

    @PastOrPresent(message = "Creation date cannot be in the future")
    @NotNull(message = "Creation date cannot be null")
    private LocalDateTime creationDate;

    private List<FieldCreateDTO> fields;

}

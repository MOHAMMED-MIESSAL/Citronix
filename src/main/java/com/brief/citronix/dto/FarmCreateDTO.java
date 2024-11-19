package com.brief.citronix.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * DTO for creating a new Farm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmCreateDTO {

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Location cannot be blank")
    @NotNull(message = "Location cannot be null")
    private String location;

    @Positive(message = "Area must be greater than 0")
    private double area;

    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDateTime creationDate;

}

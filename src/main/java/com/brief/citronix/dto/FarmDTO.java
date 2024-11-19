package com.brief.citronix.dto;

import lombok.*;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Farm entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmDTO implements Serializable {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")

    private String name;

    @NotBlank(message = "Location cannot be blank")
    @NotNull(message = "Location cannot be null")
    private String location;

    @Positive(message = "Area must be greater than 0")
    private double area;

    @PastOrPresent(message = "Creation date must be in the past or present")
    @NotNull(message = "Creation date cannot be null")
    private LocalDateTime creationDate;
}

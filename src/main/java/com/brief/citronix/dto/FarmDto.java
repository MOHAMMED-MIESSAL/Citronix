package com.brief.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.brief.citronix.domain.Farm}
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FarmDto implements Serializable {

    @NotBlank(message = "The name cannot be blank.")
    @Size(max = 30, message = "The name must not exceed 30 characters.")
    @Size(min = 3, message = "The name must be at least 3 characters.")
    @NotNull(message = "The name cannot be null.")
    String name;

    @NotBlank(message = "The location cannot be blank.")
    @Size(max = 100, message = "The location must not exceed 100 characters.")
    @Size(min = 3, message = "The location must be at least 3 characters.")
    @NotNull(message = "The location cannot be null.")
    String location;

    @Positive(message = "The area must be a positive value.")
    @DecimalMax(value = "1000.0", message = "The area cannot exceed 1000 hectares.")
    double area;

    @NotNull(message = "The creation date cannot be null.")
    @PastOrPresent(message = "The creation date must be in the past or present.")
    @NotNull(message = "The creation date cannot be null.")
    LocalDateTime creationDate;
}
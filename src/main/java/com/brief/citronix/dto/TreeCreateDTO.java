package com.brief.citronix.dto;


import com.brief.citronix.domain.Field;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class TreeCreateDTO {

    private UUID id;

    //    @JsonProperty("date_plantation")
    @NotNull(message = "Date of plantation cannot be null")
    @PastOrPresent(message = "Date of plantation must be in the past or present")
    private LocalDateTime datePlantation;

    //     @JsonProperty("field_id")
    @NotNull(message = "Field ID cannot be null")
    private UUID fieldId;

    private FieldCreateDTO field;
}

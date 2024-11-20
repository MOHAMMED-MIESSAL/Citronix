package com.brief.citronix.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for creating a new Farm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeDTO implements Serializable {

    private UUID id;
    private LocalDateTime datePlantation;
    private FieldDTO fieldDTO;
}

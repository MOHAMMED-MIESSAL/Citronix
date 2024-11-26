package com.brief.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


/**
 * DTO for creating a new Farm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestCreateRequest {
    private UUID fieldId;
    private HarvestCreateDTO harvestCreateDTO;
}

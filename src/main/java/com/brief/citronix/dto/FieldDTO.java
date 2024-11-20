package com.brief.citronix.dto;


import com.brief.citronix.domain.Farm;
import com.brief.citronix.viewmodel.FarmVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object for Farm entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO implements Serializable {

    private UUID id;
    private double area;
    private FarmDTO farmDTO;

/**
 * SI on veut juste l'ID du farm
 */

//    @JsonProperty("farm_id")
//    private UUID farmId;


}

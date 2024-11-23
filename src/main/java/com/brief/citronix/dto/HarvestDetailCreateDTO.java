package com.brief.citronix.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailCreateDTO {

    private UUID id;
    private double quantity;
    private UUID harvestId;
    private UUID treeId;

}

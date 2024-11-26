package com.brief.citronix.mapper;


import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import com.brief.citronix.viewmodel.HarvestDetailVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {

    /**
     * Map HarvestDetailCreateDTO to HarvestDetail
     */

    @Mapping(source = "harvestId", target = "harvest.id")
    @Mapping(source = "treeId", target = "tree.id")
    HarvestDetail toHarvestDetail(HarvestDetailCreateDTO harvestDetailCreateDTO);

    /**
     * Map HarvestDetail to HarvestDetailVM
     */
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "harvest", target = "harvest")
    HarvestDetailVM toHarvestDetailVM(HarvestDetail harvestDetail);

}

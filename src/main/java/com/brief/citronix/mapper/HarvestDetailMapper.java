package com.brief.citronix.mapper;


import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import com.brief.citronix.viewmodel.HarvestDetailVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {

    @Mapping(source = "harvestId", target = "harvest.id")
    @Mapping(source = "treeId", target = "tree.id")
    HarvestDetail toHarvestDetail(HarvestDetailCreateDTO harvestDetailCreateDTO);

    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "harvest", target = "harvest")
    @Mapping(source = "tree", target = "tree")
    HarvestDetailVM toHarvestDetailVM(HarvestDetail harvestDetail);
}

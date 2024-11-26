package com.brief.citronix.mapper;


import com.brief.citronix.domain.Harvest;
import com.brief.citronix.dto.HarvestCreateDTO;
import com.brief.citronix.viewmodel.HarvestVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {SaleMapper.class ,  HarvestDetailMapper.class})
public interface HarvestMapper {

    /**
     * Map HarvestCreateDTO to Harvest
     */
    @Mapping(source = "sales", target = "sales")
    Harvest toHarvest(HarvestCreateDTO harvestCreateDTO);

    /**
     * Map Harvest to HarvestVM
     */
    @Mapping(source = "harvestDate", target = "harvestDate")
    @Mapping(source = "totalQuantity", target = "totalQuantity")
    @Mapping(source = "season", target = "season")
    HarvestVM toHarvestVM(Harvest harvest);
}

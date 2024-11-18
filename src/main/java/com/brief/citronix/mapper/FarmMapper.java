package com.brief.citronix.mapper;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmDto;
import com.brief.citronix.viewmodel.FarmVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "creationDate", target = "creationDate")
    Farm farmDtoToFarm(FarmDto farmDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "creationDate", target = "creationDate")
    FarmVM farmToFarmVM(Farm farm);
}

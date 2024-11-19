package com.brief.citronix.mapper;


import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmCreateDTO;
import com.brief.citronix.dto.FarmDTO;
import com.brief.citronix.viewmodel.FarmVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toFarm(FarmCreateDTO farmCreateDTO);
    FarmDTO toFarmDTO(Farm farm);
    FarmVM toFarmVM(FarmDTO farmDTO);
}

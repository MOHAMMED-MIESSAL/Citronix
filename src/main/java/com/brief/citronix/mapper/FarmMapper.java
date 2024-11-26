    package com.brief.citronix.mapper;


    import com.brief.citronix.domain.Farm;
    import com.brief.citronix.dto.FarmCreateDTO;
    import com.brief.citronix.viewmodel.FarmVM;
    import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring" , uses = { HarvestDetailMapper.class})
    public interface FarmMapper {
        /**
         * Mapping from FarmCreateDTO to Farm
         */
        Farm toFarm(FarmCreateDTO farmCreateDTO);

        /**
         * Mapping from Farm to FarmVM
         */
        FarmVM toFarmVM(Farm farm);
    }

    package com.brief.citronix.mapper;


    import com.brief.citronix.domain.Farm;
    import com.brief.citronix.dto.FarmCreateDTO;
    import com.brief.citronix.viewmodel.FarmVM;
    import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring")
    public interface FarmMapper {
        /**
         * Mapping from FarmCreateDTO to Farm
         * @param farmCreateDTO
         * @return Farm
         */
        Farm toFarm(FarmCreateDTO farmCreateDTO);

        /**
         * Mapping from Farm to FarmVM
         * @param farm
         * @return FarmVM
         */
        FarmVM toFarmVM(Farm farm);
    }

package com.brief.citronix.mapper;


import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.dto.FieldDTO;
import com.brief.citronix.viewmodel.FieldVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    Field toField(FieldCreateDTO fieldCreateDTO);

    /**
     * SI on veut juste l'ID du farm
     */
    //    @Mapping(source = "farm.id", target = "farmId")


    @Mapping(source = "farm", target = "farmDTO")
    FieldDTO toFieldDTO(Field field);

    FieldVM toFieldVM(FieldDTO fieldDTO);


}



package com.brief.citronix.mapper;


import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.viewmodel.FieldVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    /**
     * Map FieldCreateDTO to Field
     * @param fieldCreateDTO
     * @return
     */
    Field toField(FieldCreateDTO fieldCreateDTO);

    /**
     * Map Field to FieldVM
     * @param field
     * @return
     */
    @Mapping(source = "farm", target = "farm")
    FieldVM toFieldVM(Field field);
}



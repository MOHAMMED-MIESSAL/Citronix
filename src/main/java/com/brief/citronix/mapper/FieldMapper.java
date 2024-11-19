package com.brief.citronix.mapper;


import com.brief.citronix.dto.FieldDto;
import com.brief.citronix.viewmodel.FieldVM;
import org.mapstruct.Mapper;
import com.brief.citronix.domain.Field;


@Mapper(componentModel = "spring", uses = { FarmMapper.class })
public interface FieldMapper {
    Field fieldDtoToField(FieldDto fieldDto);
    FieldDto fieldToFieldDto(Field field);
    FieldVM fieldDtoToFieldVM(FieldDto fieldDto);
    FieldVM fieldToFieldVM(Field field);


}

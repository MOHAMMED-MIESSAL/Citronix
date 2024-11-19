package com.brief.citronix.service;

import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldDto;
import com.brief.citronix.viewmodel.FieldVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FieldService {
    void delete(UUID id);
    FieldDto save(Field field);
    FieldDto update(UUID id, FieldDto fieldDto);
    Optional<FieldDto> findFieldById(UUID id);
    Page<FieldDto> findAll(Pageable pageable);
}

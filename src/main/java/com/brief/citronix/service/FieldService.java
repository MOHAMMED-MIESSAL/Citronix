package com.brief.citronix.service;

import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldService {

    Page<Field> findAll(Pageable pageable);
    Field save(FieldCreateDTO fieldCreateDTO);
    Field update(UUID id, FieldCreateDTO fieldCreateDTO);
    void delete(UUID id);
    Optional<Field> findFieldById(UUID id);
    List<Field> findByFarmId(UUID farmId);
    long countByFarmId(UUID farmId);
}

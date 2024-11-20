package com.brief.citronix.service;

import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.dto.FieldDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FieldService {
    Page<FieldDTO> findAll(Pageable pageable);
    FieldDTO save(FieldCreateDTO fieldCreateDTO);
    FieldDTO update(UUID id, FieldCreateDTO fieldCreateDTO);
    void delete(UUID id);
    Optional<FieldDTO> findFieldById(UUID id);
}

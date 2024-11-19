package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldDto;
import com.brief.citronix.mapper.FieldMapper;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.service.FieldService;
import com.brief.citronix.viewmodel.FieldVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository , FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public Page<FieldDto> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable)
                .map(fieldMapper::fieldToFieldDto);
    }


    @Override
    public void delete(UUID id) {

    }

    @Override
    public Optional<FieldDto> findFieldById(UUID id) {
        return Optional.empty();
    }

    @Override
    public FieldDto save(Field field) {
        // Vérifier que la ferme associée existe
        if (field.getFarm() == null || field.getFarm().getId() == null) {
            throw new IllegalArgumentException("A valid farm must be associated with the field.");
        }

        Farm farm = farmRepository.findById(field.getFarm().getId())
                .orElseThrow(() -> new IllegalArgumentException("Farm with ID " + field.getFarm().getId() + " not found"));

        // Associer la ferme au champ
        field.setFarm(farm);

        // Sauvegarder l'entité Field et retourner le DTO
        Field savedField = fieldRepository.save(field);
        return fieldMapper.fieldToFieldDto(savedField);
    }





    @Override
    public FieldDto update(UUID id, FieldDto fieldDto) {
        return null;
    }
}

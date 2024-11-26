package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.exception.CustomValidationException;
import com.brief.citronix.mapper.FieldMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.service.FarmService;
import com.brief.citronix.service.FieldService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmService farmService;
    private final FieldMapper fieldMapper;


    @Override
    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }

    @Override
    public Field save(FieldCreateDTO fieldCreateDTO) {
        UUID farmId = fieldCreateDTO.getFarmId();

        // Check Constraints
        validateFieldAreaConstraints(fieldCreateDTO, farmId, null);

        // Save the field
        Field field = fieldMapper.toField(fieldCreateDTO);
        field.setFarm(farmService.findFarmById(farmId).orElseThrow());
        return fieldRepository.save(field);
    }

    @Override
    public Field update(UUID id, FieldCreateDTO fieldCreateDTO) {
        // Vérifier si le champ existe
        Field existingField = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + id + " not found"));

        UUID farmId = fieldCreateDTO.getFarmId();

        // Check Constraints
        validateFieldAreaConstraints(fieldCreateDTO, farmId, id);

        existingField.setId(id);
        existingField.setArea(fieldCreateDTO.getArea());
        existingField.setFarm(farmService.findFarmById(farmId).orElseThrow());
        return fieldRepository.save(existingField);
    }

    @Override
    public void delete(UUID id) {
        fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + id + " not found"));

        fieldRepository.deleteById(id);
    }

    @Override
    public Optional<Field> findFieldById(UUID id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);
        if (fieldOptional.isEmpty()) {
            throw new EntityNotFoundException("Farm with ID " + id + " not found");
        }
        return fieldOptional;
    }

    @Override
    public List<Field> findByFarmId(UUID farmId) {
        return fieldRepository.findByFarmId(farmId);
    }

    @Override
    public long countByFarmId(UUID farmId) {
        return fieldRepository.countByFarmId(farmId);
    }

    // Helper method
    private void validateFieldAreaConstraints(FieldCreateDTO fieldCreateDTO, UUID farmId, UUID excludedFieldId) {
        Optional<Farm> farmOptional = farmService.findFarmById(farmId);
        if (farmOptional.isEmpty()) {
            throw new EntityNotFoundException("Farm with ID " + farmId + " not found");
        }

        Farm farm = farmOptional.get();

        // Calculer la surface totale des champs, excluant un champ si spécifié
        double totalFieldArea = farm.getFields().stream()
                .filter(field -> !field.getId().equals(excludedFieldId))
                .mapToDouble(Field::getArea)
                .sum();

        double newFieldArea = fieldCreateDTO.getArea();

        // Vérifier si la surface totale des champs dépasse la surface de la ferme
        if (totalFieldArea + newFieldArea >= farm.getArea()) {
            throw new CustomValidationException("The total area of the fields must be strictly less than the total area of the farm.");
        }

        // Vérifier si la ferme a plus de 10 champs
        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= 10) {
            throw new CustomValidationException("Farm with ID " + farmId + " cannot have more than 10 fields.");
        }

        // Vérifier si la surface du champ est inférieure à 50 % de la surface de la ferme
        if (newFieldArea > farm.getArea() * 0.5) {
            throw new CustomValidationException("Field area cannot exceed 50% of the farm's total area.");
        }
    }


}

package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.dto.FieldDTO;
import com.brief.citronix.exception.CustomValidationException;
import com.brief.citronix.mapper.FieldMapper;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.service.FieldService;
import jakarta.persistence.EntityNotFoundException;
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

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public Page<FieldDTO> findAll(Pageable pageable) {
        Page<Field> fields = fieldRepository.findAll(pageable);
        return fields.map(fieldMapper::toFieldDTO);
    }

    @Override
    public FieldDTO save(FieldCreateDTO fieldCreateDTO) {
        UUID farmId = fieldCreateDTO.getFarmId();
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new EntityNotFoundException("Farm with ID " + farmId + " not found"));

        // Calculate the total area of all fields in the farm
        double totalFieldArea = farm.getFields().stream()
                .mapToDouble(Field::getArea)
                .sum();

        // Check if the total area of the fields exceeds the total area of the farm
        double newFieldArea = fieldCreateDTO.getArea();
        if (totalFieldArea + newFieldArea > farm.getArea()) {
            throw new CustomValidationException("The total area of the fields cannot exceed the total area of the farm.");
        }

        // Check if the farm has more than 10 fields
        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= 10) {
            throw new CustomValidationException("Farm with ID " + farmId + " cannot have more than 10 fields.");
        }

        // Check if the field area is at least 0.1 hectares (done in the Controller using @Valid)
        double area = fieldCreateDTO.getArea();

        // Check if the field area is less than 50% of the farm's total area
        if (area > farm.getArea() * 0.5) {
            throw new CustomValidationException("Field area cannot exceed 50% of the farm's total area.");
        }

        // Save the field
        Field field = fieldMapper.toField(fieldCreateDTO);
        field.setFarm(farm);
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toFieldDTO(savedField);
    }

    @Override
    public FieldDTO update(UUID id, FieldCreateDTO fieldCreateDTO) {
        // Check if the field exists
        Field field = fieldRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Field with ID " + id + " not found")
        );

        // Check if the farm exists
        UUID farmId = fieldCreateDTO.getFarmId();
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new EntityNotFoundException("Farm with ID " + farmId + " not found"));

        // Calculate the total area of all fields in the farm, excluding the field being updated
        double totalFieldArea = farm.getFields().stream()
                .filter(f -> !f.getId().equals(id))
                .mapToDouble(Field::getArea)
                .sum();

        double newFieldArea = fieldCreateDTO.getArea();

        // Check if the total area of the fields exceeds the total area of the farm
        if (totalFieldArea + newFieldArea > farm.getArea()) {
            throw new CustomValidationException("The total area of the fields cannot exceed the total area of the farm.");
        }

        // Check if the field area is at least 0.1 hectares
        if (newFieldArea < 0.1) {
            throw new CustomValidationException("Field area must be at least 0.1 hectares (1,000 m²).");
        }

        // Check if the field area is less than 50% of the farm's total area
        if (newFieldArea > farm.getArea() * 0.5) {
            throw new CustomValidationException("Field area cannot exceed 50% of the farm's total area.");
        }

        // Update the field
        field.setArea(newFieldArea);
        field.setFarm(farm);

        Field updatedField = fieldRepository.save(field);
        return fieldMapper.toFieldDTO(updatedField);
    }

    @Override
    public void delete(UUID id) {
        Optional<Field> field = fieldRepository.findById(id);
        if (field.isEmpty()) {
            throw new EntityNotFoundException("Field with ID " + id + " not found");
        }
        fieldRepository.deleteById(id);
    }

    @Override
    public Optional<FieldDTO> findFieldById(UUID id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);
        if (fieldOptional.isEmpty()) {
            throw new EntityNotFoundException("Farm with ID " + id + " not found");
        }
        return fieldOptional.map(fieldMapper::toFieldDTO);
    }




}

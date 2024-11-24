package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Field;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.exception.CustomValidationException;
import com.brief.citronix.mapper.TreeMapper;
import com.brief.citronix.repository.TreeRepository;
import com.brief.citronix.service.FieldService;
import com.brief.citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldService fieldService;
    private final TreeMapper treeMapper;


    @Override
    public Page<Tree> findAll(Pageable pageable) {
        return treeRepository.findAll(pageable);
    }

    @Override
    public Tree save(TreeCreateDTO treeCreateDTO) {
        UUID fieldId = treeCreateDTO.getFieldId();
        Field field = fieldService.findFieldById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + fieldId + " not found"));

        // Check if the field can have more trees
        if (!canAddTreeToField(field)) {
            throw new CustomValidationException(
                    "Cannot add more trees to this field. Maximum density of 10 trees per 1,000 m² exceeded (0.1 hectare) " +
                            "Field area: " + field.getArea() + " hectares."
            );
        }

        // Check if the planting date is valid
        validatePlantingDate(treeCreateDTO.getDatePlantation());

        Tree tree = treeMapper.toTree(treeCreateDTO);
        tree.setField(field);
        return treeRepository.save(tree);
    }

    @Override
    public Tree update(UUID id, TreeCreateDTO treeCreateDTO) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with ID " + id + " not found"));

        UUID fieldId = treeCreateDTO.getFieldId();
        Field field = fieldService.findFieldById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + fieldId + " not found"));

        // Validate planting date
        validatePlantingDate(treeCreateDTO.getDatePlantation());

        tree.setId(id);
        tree.setField(field);
        tree.setDatePlantation(treeCreateDTO.getDatePlantation());
        return treeRepository.save(tree);
    }

    @Override
    public Optional<Tree> findTreeById(UUID id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with ID " + id + " not found"));
        return Optional.of(tree);
    }

    @Override
    public void delete(UUID id) {
        Optional<Tree> tree = findTreeById(id);
        if (tree.isPresent()) {
            treeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Tree with ID " + id + " not found");
        }
    }

    @Override
    public List<Tree> findAllByField(UUID fieldId) {
        return treeRepository.findAllByField(fieldId);
    }

    // Helper methods
    public boolean canAddTreeToField(Field field) {
        double areaInSquareMeters = field.getArea() * 10_000;
        double maxTrees = (areaInSquareMeters / 1_000) * 10; // 10 trees per 1,000 m²
        long currentTreeCount = treeRepository.countByField(field);
        return currentTreeCount < maxTrees;
    }
    private void validatePlantingDate(LocalDateTime datePlantation) {
        int month = datePlantation.getMonthValue();
        if (month < 3 || month > 5) {
            throw new CustomValidationException(
                    "Trees can only be planted between March and May "
            );
        }
    }


}

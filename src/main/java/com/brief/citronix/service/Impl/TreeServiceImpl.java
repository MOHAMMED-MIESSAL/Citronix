package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Field;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.dto.TreeDTO;
import com.brief.citronix.exception.CustomValidationException;
import com.brief.citronix.mapper.TreeMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.repository.TreeRepository;
import com.brief.citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final TreeMapper treeMapper;

    public TreeServiceImpl(TreeRepository treeRepository, FieldRepository fieldRepository, TreeMapper treeMapper) {
        this.treeRepository = treeRepository;
        this.fieldRepository = fieldRepository;
        this.treeMapper = treeMapper;
    }

    @Override
    public Page<TreeDTO> findAll(Pageable pageable) {
        return treeRepository.findAll(pageable).map(treeMapper::toTreeDTO);
    }

    @Override
    public TreeDTO save(TreeCreateDTO treeCreateDTO) {
        UUID fieldId = treeCreateDTO.getFieldId();
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + fieldId + " not found"));

        Tree tree = treeMapper.toTree(treeCreateDTO);
        tree.setField(field);
        Tree savedTree = treeRepository.save(tree);
        return treeMapper.toTreeDTO(savedTree);
    }

    @Override
    public TreeDTO update(UUID id, TreeCreateDTO treeCreateDTO) {

        Optional<TreeDTO> existingTree = Optional.ofNullable(findTreeById(id)
                .orElseThrow(() -> new CustomValidationException("Tree with ID " + id + " not found")));

        Field field = fieldRepository.findById(treeCreateDTO.getFieldId())
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + treeCreateDTO.getFieldId() + " not found"));


        Tree tree = treeMapper.toTree(treeCreateDTO);
        tree.setId(id);
        tree.setField(field);
        Tree updatedTree = treeRepository.save(tree);
        return treeMapper.toTreeDTO(updatedTree);

    }

    @Override
    public Optional<TreeDTO> findTreeById(UUID id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with ID " + id + " not found"));
        return Optional.of(treeMapper.toTreeDTO(tree));
    }

    @Override
    public void delete(UUID id) {
        Optional<TreeDTO> tree = findTreeById(id);
        if (tree.isPresent()) {
            treeRepository.deleteById(id);
        } else {
            throw new CustomValidationException("Tree with ID " + id + " not found");
        }
    }
}

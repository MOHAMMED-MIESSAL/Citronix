package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Field;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.mapper.TreeMapper;
import com.brief.citronix.repository.TreeRepository;
import com.brief.citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldServiceImpl fieldServiceImpl;
    private final TreeMapper treeMapper;


    public TreeServiceImpl(TreeRepository treeRepository, FieldServiceImpl fieldServiceImpl, TreeMapper treeMapper) {
        this.treeRepository = treeRepository;
        this.fieldServiceImpl = fieldServiceImpl;
        this.treeMapper = treeMapper;
    }

    @Override
    public Page<Tree> findAll(Pageable pageable) {
        return treeRepository.findAll(pageable);
    }

    @Override
    public Tree save(TreeCreateDTO treeCreateDTO) {
        UUID fieldId = treeCreateDTO.getFieldId();
        Field field = fieldServiceImpl.findFieldById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + fieldId + " not found"));

        Tree tree = treeMapper.toTree(treeCreateDTO);
        tree.setField(field);
        return treeRepository.save(tree);
    }

    @Override
    public Tree update(UUID id, TreeCreateDTO treeCreateDTO) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with ID " + id + " not found"));

        UUID fieldId = treeCreateDTO.getFieldId();
        Field field = fieldServiceImpl.findFieldById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + fieldId + " not found"));

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
    public List<Tree> findTreesByFieldId(UUID fieldId) {
        return treeRepository.findTreesByFieldId(fieldId);
    }
}

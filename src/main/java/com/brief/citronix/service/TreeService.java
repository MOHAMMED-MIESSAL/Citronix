package com.brief.citronix.service;

import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeService {
    Page<Tree> findAll(Pageable pageable);
    Tree save(TreeCreateDTO treeCreateDTO);
    Tree update(UUID id,TreeCreateDTO treeCreateDTO);
    void delete(UUID id);
    Optional<Tree> findTreeById(UUID id);
}

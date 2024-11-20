package com.brief.citronix.service;

import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.dto.TreeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeService {
    Page<TreeDTO> findAll(Pageable pageable);
    TreeDTO save(TreeCreateDTO treeCreateDTO);
    TreeDTO update(UUID id,TreeCreateDTO treeCreateDTO);
    void delete(UUID id);
    Optional<TreeDTO> findTreeById(UUID id);

}

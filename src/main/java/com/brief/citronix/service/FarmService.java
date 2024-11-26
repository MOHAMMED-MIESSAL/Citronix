package com.brief.citronix.service;


import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {

    Page<Farm> findAll(Pageable pageable);
    Farm save(FarmCreateDTO farmCreateDTO);
    Farm update(UUID id, FarmCreateDTO farmCreateDTO);
    void delete(UUID id);
    Optional<Farm> findFarmById(UUID id);
    Page<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}

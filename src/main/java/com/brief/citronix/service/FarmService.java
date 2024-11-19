package com.brief.citronix.service;

import com.brief.citronix.dto.FarmDto;
import com.brief.citronix.viewmodel.FarmVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {
    Page<FarmVM> findAll(Pageable pageable);
    FarmVM save(FarmDto farmDto);
    FarmVM update(UUID id,FarmDto farmDto);
    void delete(UUID id);
    Optional<FarmVM> findFarmById(UUID id);
    Page<FarmVM> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}

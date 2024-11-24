package com.brief.citronix.service;


import com.brief.citronix.domain.Harvest;
import com.brief.citronix.dto.HarvestCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface HarvestService {
    Page<Harvest> findAll(Pageable pageable);
    Optional<Harvest> findHarvestById(UUID id);
    Harvest save(HarvestCreateDTO harvestCreateDTO);
    Harvest update(UUID id, HarvestCreateDTO harvestCreateDTO);
    void delete(UUID id);

}

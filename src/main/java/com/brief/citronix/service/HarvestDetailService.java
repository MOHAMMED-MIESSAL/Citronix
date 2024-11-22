package com.brief.citronix.service;

import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface HarvestDetailService {
    Page<HarvestDetail> findAll(Pageable pageable);
    Optional<HarvestDetail> findHarvestDetailById(UUID id);
    HarvestDetail save(HarvestDetailCreateDTO harvestDetailCreateDTO);
    HarvestDetail update(UUID id, HarvestDetailCreateDTO harvestDetailCreateDTO);
    void delete(UUID id);
}

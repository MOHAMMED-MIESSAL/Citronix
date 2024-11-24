package com.brief.citronix.service;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import com.brief.citronix.enums.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HarvestDetailService {
    Page<HarvestDetail> findAll(Pageable pageable);
    Optional<HarvestDetail> findHarvestDetailById(UUID id);
    HarvestDetail save(HarvestDetailCreateDTO harvestDetailCreateDTO);
    HarvestDetail update(UUID id, HarvestDetailCreateDTO harvestDetailCreateDTO);
    void delete(UUID id);
    List<HarvestDetail> saveAll(List<HarvestDetail> harvestDetails);
    boolean existsByFieldAndSeason(UUID fieldId, Season season);
    boolean existsByTreeAndHarvest_Season(Tree tree, Season season);
    boolean isTreeInHarvest(Tree tree, Harvest harvest);
    boolean deleteAllByHarvest(Harvest harvest);
}

package com.brief.citronix.repository;

import com.brief.citronix.domain.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {
    List<HarvestDetail> findByHarvestId(UUID harvestId); // Fetch details for a harvest
    List<HarvestDetail> findByTreeId(UUID treeId); // Fetch details for a tree
}

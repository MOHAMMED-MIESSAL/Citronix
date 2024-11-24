package com.brief.citronix.repository;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {
    List<HarvestDetail> findByHarvestId(UUID harvestId); // Fetch details for a harvest
    List<HarvestDetail> findByTreeId(UUID treeId); // Fetch details for a tree
    boolean isTreeInHarvest(Tree tree, Harvest harvest);
    boolean deleteAllByHarvest(Harvest harvest);

    @Query("SELECT CASE WHEN COUNT(hd) > 0 THEN TRUE ELSE FALSE END " +
            "FROM HarvestDetail hd WHERE hd.tree.field.id = :fieldId AND hd.harvest.season = :season")
    boolean existsByFieldAndSeason(@Param("fieldId") UUID fieldId, @Param("season") Season season);

    @Query("SELECT CASE WHEN COUNT(hd) > 0 THEN TRUE ELSE FALSE END " +
            "FROM HarvestDetail hd WHERE hd.tree = :tree AND hd.harvest.season = :season")
    boolean existsByTreeAndHarvest_Season(@Param("tree") Tree tree, @Param("season") Season season);
}

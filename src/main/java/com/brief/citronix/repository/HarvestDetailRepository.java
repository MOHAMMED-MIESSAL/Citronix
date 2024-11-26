package com.brief.citronix.repository;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {

    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd " +
            "JOIN hd.harvest h " +
            "WHERE hd.tree.id = :treeId " +
            "AND h.season = :season")
    boolean existsByTreeAndSeason(@Param("treeId") UUID treeId,
                                  @Param("season") Season season);


    @Query(value = "SELECT EXISTS (" +
            "    SELECT 1 FROM harvest_detail hd " +
            "    JOIN harvest h ON hd.harvest_id = h.id " +
            "    WHERE hd.tree_id = :treeId " +
            "    AND h.season = :season" +
            ")", nativeQuery = true)
    boolean isTreeHarvestedInSeason(@Param("treeId") UUID treeId,
                                    @Param("season") Season season);


    void deleteAllByHarvestId(UUID harvestId);
}

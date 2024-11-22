package com.brief.citronix.repository;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    Optional<Harvest> findBySeason(Season season);
}

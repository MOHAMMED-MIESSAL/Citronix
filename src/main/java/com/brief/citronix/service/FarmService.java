package com.brief.citronix.service;


import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {

    /**
     * Find all farms.
     *
     * @param pageable pagination information
     * @return a page of Farm objects
     */
    Page<Farm> findAll(Pageable pageable);

    /**
     * Create a farm.
     *
     * @param farmCreateDTO the farm creation data transfer object
     * @return the created Farm object
     */
    Farm save(FarmCreateDTO farmCreateDTO);

    /**
     * Update a farm by its ID.
     *
     * @param id the ID of the farm to update
     * @param farmCreateDTO the farm creation data transfer object
     * @return the updated Farm object
     */
    Farm update(UUID id, FarmCreateDTO farmCreateDTO);

    /**
     * Delete a farm by its ID.
     *
     * @param id the ID of the farm to delete
     */
    void delete(UUID id);

    /**
     * Find a farm by its ID.
     *
     * @param id the ID of the farm to find
     * @return an optional Farm object
     */
    Optional<Farm> findFarmById(UUID id);

    /**
     * Search for farms by name, location, area, and date range.
     *
     * @param name the name of the farm
     * @param location the location of the farm
     * @param minArea the minimum area of the farm
     * @param maxArea the maximum area of the farm
     * @param startDate the start date of the farm
     * @param endDate the end date of the farm
     * @param pageable pagination information
     * @return a page of Farm objects
     */
    Page<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}

package com.brief.citronix.service;


import com.brief.citronix.dto.FarmCreateDTO;
import com.brief.citronix.dto.FarmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {

    /**
     * Retrieve all farms with pagination.
     *
     * @param pageable pagination information
     * @return a page of FarmDTO objects
     */
    Page<FarmDTO> findAll(Pageable pageable);

    /**
     * Save a new farm.
     *
     * @param farmCreateDTO the farm creation data transfer object
     * @return the saved FarmDTO
     */
    FarmDTO save(FarmCreateDTO farmCreateDTO);

    /**
     * Update an existing farm.
     *
     * @param id the ID of the farm to update
     * @param farmCreateDTO the farm creation data transfer object
     * @return the updated FarmDTO
     */
    FarmDTO update(UUID id, FarmCreateDTO farmCreateDTO);

    /**
     * Delete a farm by its ID.
     *
     * @param id the ID of the farm to delete
     */
    void delete(UUID id);

    /**
     * Find a farm by its ID.
     *
     * @param id the ID of the farm
     * @return an Optional containing the FarmDTO, or empty if not found
     */
    Optional<FarmDTO> findFarmById(UUID id);

    /**
     * Search for farms by name, location, area, and date range.
     *
     * @param name the name of the farm
     * @param location the location of the farm
     * @param minArea the minimum area of the farm
     * @param startDate the start date of the farm
     * @param endDate the end date of the farm
     * @param maxArea the maximum area of the farm
     * @param pageable pagination information
     * @return a page of FarmDTO objects
     */
    Page<FarmDTO> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}

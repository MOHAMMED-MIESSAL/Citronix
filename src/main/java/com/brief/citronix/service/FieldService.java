package com.brief.citronix.service;

import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldService {
    /**
     * Retrieve all fields with pagination.
     *
     * @param pageable pagination information
     * @return a page of FieldDTO objects
     */
    Page<Field> findAll(Pageable pageable);

    /**
     * Save a new field.
     *
     * @param fieldCreateDTO the field creation data transfer object
     * @return the saved FieldDTO
     */
    Field save(FieldCreateDTO fieldCreateDTO);

    /**
     * Update an existing field.
     *
     * @param id the ID of the field to update
     * @param fieldCreateDTO the field creation data transfer object
     * @return the updated FieldDTO
     */
    Field update(UUID id, FieldCreateDTO fieldCreateDTO);

    /**
     * Delete a field by its ID.
     *
     * @param id the ID of the field to delete
     */
    void delete(UUID id);

    /**
     * Find a field by its ID.
     *
     * @param id the ID of the field
     * @return an Optional containing the FieldDTO, or empty if not found
     */
    Optional<Field> findFieldById(UUID id);

    /**
     * Find all fields by farm ID.
     *
     * @param farmId the ID of the farm
     * @return a list of FieldDTO objects
     */
    List<Field> findByFarmId(UUID farmId);

    /**
     * Count the number of fields by farm ID.
     *
     * @param farmId the ID of the farm
     * @return the number of fields
     */
    long countByFarmId(UUID farmId);
}

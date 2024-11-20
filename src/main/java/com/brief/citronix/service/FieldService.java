package com.brief.citronix.service;

import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.dto.FieldDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FieldService {
    /**
     * Retrieve all fields with pagination.
     *
     * @param pageable pagination information
     * @return a page of FieldDTO objects
     */
    Page<FieldDTO> findAll(Pageable pageable);

    /**
     * Save a new field.
     *
     * @param fieldCreateDTO the field creation data transfer object
     * @return the saved FieldDTO
     */
    FieldDTO save(FieldCreateDTO fieldCreateDTO);

    /**
     * Update an existing field.
     *
     * @param id the ID of the field to update
     * @param fieldCreateDTO the field creation data transfer object
     * @return the updated FieldDTO
     */
    FieldDTO update(UUID id, FieldCreateDTO fieldCreateDTO);

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
    Optional<FieldDTO> findFieldById(UUID id);
}

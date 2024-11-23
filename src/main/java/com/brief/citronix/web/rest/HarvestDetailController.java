package com.brief.citronix.web.rest;


import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import com.brief.citronix.mapper.HarvestDetailMapper;
import com.brief.citronix.service.HarvestDetailService;
import com.brief.citronix.viewmodel.HarvestDetailVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/harvest-details")
@RequiredArgsConstructor
public class HarvestDetailController {

    private final HarvestDetailService harvestDetailService;
    private final HarvestDetailMapper harvestDetailMapper;

    /**
     * Get all harvest details
     */
    @GetMapping
    public ResponseEntity<Page<HarvestDetailVM>> getAllHarvestDetails(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(harvestDetailService.findAll(pageable)
                .map(harvestDetailMapper::toHarvestDetailVM));
    }

    /**
     * Create a new harvest detail
     */
    @PostMapping
    public ResponseEntity<HarvestDetailVM> createHarvestDetail(@Valid @RequestBody HarvestDetailCreateDTO harvestDetailCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(harvestDetailMapper.toHarvestDetailVM(harvestDetailService.save(harvestDetailCreateDTO)));
    }

    /**
     * Get a harvest detail by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<HarvestDetailVM> getHarvestDetailById(@PathVariable UUID id) {
        Optional<HarvestDetail> harvestDetail = harvestDetailService.findHarvestDetailById(id);
        return harvestDetail.map(value -> ResponseEntity.ok(harvestDetailMapper.toHarvestDetailVM(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update a harvest detail
     */
    @PutMapping("/{id}")
    public ResponseEntity<HarvestDetailVM> updateHarvestDetail(@PathVariable UUID id, @Valid @RequestBody HarvestDetailCreateDTO harvestDetailCreateDTO) {
        return ResponseEntity.ok(harvestDetailMapper.toHarvestDetailVM(harvestDetailService.update(id, harvestDetailCreateDTO)));
    }

    /**
     * Delete a harvest detail
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvestDetail(@PathVariable UUID id) {
        harvestDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.brief.citronix.web.rest;


import com.brief.citronix.domain.Harvest;
import com.brief.citronix.dto.HarvestCreateDTO;
import com.brief.citronix.mapper.HarvestMapper;
import com.brief.citronix.service.HarvestService;
import com.brief.citronix.viewmodel.HarvestVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/harvets")
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;

    public HarvestController(HarvestService harvestService, HarvestMapper harvestMapper) {
        this.harvestMapper = harvestMapper;
        this.harvestService = harvestService;
    }

    @GetMapping
    public ResponseEntity<Page<HarvestVM>> getAllHarvests(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(harvestService.findAll(pageable).map(harvestMapper::toHarvestVM));
    }

    @PostMapping
    public ResponseEntity<HarvestVM> createHarvest(@Valid @RequestBody HarvestCreateDTO harvestCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(harvestMapper.toHarvestVM(harvestService.save(harvestCreateDTO)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<HarvestVM> getHarvestById(@PathVariable UUID id) {
        Optional<Harvest> harvest = harvestService.findHarvestById(id);
        return harvest.map(value -> ResponseEntity.ok(harvestMapper.toHarvestVM(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HarvestVM> updateHarvest(@PathVariable UUID id, @Valid @RequestBody HarvestCreateDTO harvestCreateDTO) {
        return ResponseEntity.ok(harvestMapper.toHarvestVM(harvestService.update(id, harvestCreateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable UUID id) {
        harvestService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

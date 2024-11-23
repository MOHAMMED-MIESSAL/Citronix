package com.brief.citronix.web.rest;


import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmCreateDTO;
import com.brief.citronix.mapper.FarmMapper;
import com.brief.citronix.service.FarmService;
import com.brief.citronix.viewmodel.FarmVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    /*
     * Get all farms
     */
    @GetMapping
    public ResponseEntity<Page<FarmVM>> getAllFarms(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(farmService.findAll(pageable).map(farmMapper::toFarmVM));
    }

    /*
     * Create a new farm
     */
    @PostMapping
    public ResponseEntity<FarmVM> createFarm(@Valid @RequestBody FarmCreateDTO farmCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(farmMapper.toFarmVM(farmService.save(farmCreateDTO)));
    }

    /*
     * Get a farm by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<FarmVM> getFarmById(@PathVariable UUID id) {
        Optional<Farm> farm = farmService.findFarmById(id);
        return farm.map(value -> ResponseEntity.ok(farmMapper.toFarmVM(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     * Update a farm
     */
    @PutMapping("/{id}")
    public ResponseEntity<FarmVM> updateFarm(@PathVariable UUID id, @Valid @RequestBody FarmCreateDTO farmCreateDTO) {
        return ResponseEntity.ok(farmMapper.toFarmVM(farmService.update(id, farmCreateDTO)));
    }

    /*
     * Delete a farm
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable UUID id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /*
     * Search  farms
     */
    @GetMapping("/search")
    public ResponseEntity<Page<FarmVM>> searchFarms(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String location,
                                                    @RequestParam(required = false) Double minArea,
                                                    @RequestParam(required = false) Double maxArea,
                                                    @RequestParam(required = false) LocalDateTime startDate,
                                                    @RequestParam(required = false) LocalDateTime endDate,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(farmService.searchFarms(name, location, minArea, maxArea, startDate, endDate, pageable)
                .map(farmMapper::toFarmVM));
    }


}
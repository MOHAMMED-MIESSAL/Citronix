package com.brief.citronix.web.rest;


import com.brief.citronix.dto.FarmDto;
import com.brief.citronix.service.FarmService;
import com.brief.citronix.viewmodel.FarmVM;
import jakarta.validation.Valid;
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
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    /*
     * Get all farms
     */
    @GetMapping
    public Page<FarmVM> getAllFarms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return farmService.findAll(pageable);
    }

    /*
     * Create a new farm
     */
    @PostMapping
    public ResponseEntity<FarmVM> createFarm(@RequestBody @Valid FarmDto farmDto) {
        FarmVM farm = farmService.save(farmDto);
        return new ResponseEntity<>(farm, HttpStatus.CREATED);
    }

    /*
     * Get a farm by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<FarmVM> getFarm(@PathVariable UUID id) {
        Optional<FarmVM> farm = farmService.findFarmById(id);
        return farm.map(farmDto -> new ResponseEntity<>(farmDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*
     * Update a farm
     */
    @PutMapping("/{id}")
    public ResponseEntity<FarmVM> updateFarm(@PathVariable UUID id, @RequestBody @Valid FarmDto farmDto) {
        Optional<FarmVM> farm = farmService.findFarmById(id);
        if (farm.isPresent()) {
            FarmVM updatedFarm = farmService.update(id, farmDto);
            return new ResponseEntity<>(updatedFarm, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
     * Delete a farm
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable UUID id) {
        Optional<FarmVM> farm = farmService.findFarmById(id);
        if (farm.isPresent()) {
            farmService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
     * Search  farms
     */
    @GetMapping("/search")
    public ResponseEntity<Page<FarmVM>> searchFarms(@RequestParam(required = false) String name, @RequestParam(required = false) String location, @RequestParam(required = false) Double minArea, @RequestParam(required = false) Double maxArea, @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FarmVM> farms = farmService.searchFarms(name, location, minArea, maxArea, startDate, endDate, pageable);

        if (farms.isEmpty()) {
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(Page.empty(), HttpStatus.OK);
        }
        return new ResponseEntity<>(farms, HttpStatus.OK);
    }

}
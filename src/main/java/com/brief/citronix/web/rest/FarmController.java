package com.brief.citronix.web.rest;


import com.brief.citronix.dto.FarmCreateDTO;
import com.brief.citronix.dto.FarmDTO;
import com.brief.citronix.mapper.FarmMapper;
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
    private final FarmMapper farmMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }


    /*
     * Get all farms
     */
    @GetMapping
    public ResponseEntity<Page<FarmVM>> getAllFarms(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FarmDTO> farmDTOPage = farmService.findAll(pageable);
        Page<FarmVM> farmVMPage = farmDTOPage.map(farmMapper::toFarmVM);
        return ResponseEntity.ok(farmVMPage);
    }


    /*
     * Create a new farm
     */
    @PostMapping
    public ResponseEntity<FarmVM> createFarm(@Valid @RequestBody FarmCreateDTO farmCreateDTO) {
        FarmDTO farmDTO = farmService.save(farmCreateDTO);
        FarmVM farmVM = farmMapper.toFarmVM(farmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(farmVM);
    }


    /*
     * Get a farm by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<FarmVM> getFarmById(@PathVariable UUID id) {
        Optional<FarmDTO> farmDTO = farmService.findFarmById(id);
        return farmDTO.map(dto -> ResponseEntity.ok(farmMapper.toFarmVM(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /*
     * Update a farm
     */
    @PutMapping("/{id}")
    public ResponseEntity<FarmVM> updateFarm(@PathVariable UUID id, @Valid @RequestBody FarmCreateDTO farmCreateDTO) {
        FarmDTO farmDTO = farmService.update(id, farmCreateDTO);
        return ResponseEntity.ok(farmMapper.toFarmVM(farmDTO));
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
        Page<FarmDTO> farmDTOPage = farmService.searchFarms(name, location, minArea, maxArea, startDate, endDate, pageable);
        Page<FarmVM> farmVMPage = farmDTOPage.map(farmMapper::toFarmVM);
        return ResponseEntity.ok(farmVMPage);
    }


}
package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.dto.HarvestCreateDTO;
import com.brief.citronix.mapper.HarvestMapper;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.service.HarvestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestMapper harvestMapper;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestMapper harvestMapper) {
        this.harvestRepository = harvestRepository;
        this.harvestMapper = harvestMapper;
    }

    @Override
    public Page<Harvest> findAll(Pageable pageable) {
        return harvestRepository.findAll(pageable);
    }

    @Override
    public Harvest save(HarvestCreateDTO harvestCreateDTO) {
        Harvest harvest = harvestMapper.toHarvest(harvestCreateDTO);
        return harvestRepository.save(harvest);
    }

    @Override
    public Harvest update(UUID id, HarvestCreateDTO harvestCreateDTO) {
        Harvest existingHarvest = harvestRepository.findById(id).orElse(null);
        if (existingHarvest == null) {
            throw new EntityNotFoundException("Harvest with Id : " + id + " not found");
        }
        Harvest harvest = harvestMapper.toHarvest(harvestCreateDTO);
        harvest.setId(id);
        harvest.setHarvestDate(harvest.getHarvestDate());
        harvest.setTotalQuantity(harvest.getTotalQuantity());
        return harvestRepository.save(harvest);
    }

    @Override
    public void delete(UUID id) {
        Optional<Harvest> harvest = harvestRepository.findById(id);
        if (harvest.isEmpty()) {
            throw new EntityNotFoundException("Harvest with Id : " + id + " not found");
        }
        harvestRepository.deleteById(id);
    }

    @Override
    public Optional<Harvest> findHarvestById(UUID id) {
        if (harvestRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Harvest with Id : " + id + " not found");
        }
        return harvestRepository.findById(id);
    }

}

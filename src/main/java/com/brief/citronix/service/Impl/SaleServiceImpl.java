package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.Sale;
import com.brief.citronix.dto.SaleCreateDTO;
import com.brief.citronix.mapper.SaleMapper;
import com.brief.citronix.repository.SaleRepository;
import com.brief.citronix.service.HarvestService;
import com.brief.citronix.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestService harvestService;
    private final SaleMapper saleMapper;


    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }


    @Override
    public Sale save(SaleCreateDTO saleCreateDTO) {
        UUID harvestId = saleCreateDTO.getHarvestId();
        Optional<Harvest> harvest = harvestService.findHarvestById(harvestId);
        if (harvest.isEmpty()) {
            throw new EntityNotFoundException("Harvest with ID " + harvestId + " not found");
        }
        Sale sale = saleMapper.toSale(saleCreateDTO);
        sale.setHarvest(harvest.get());
        return saleRepository.save(sale);
    }

    @Override
    public Sale update(UUID id, SaleCreateDTO saleCreateDTO) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (sale.isEmpty()) {
            throw new EntityNotFoundException("Sale with ID " + id + " not found");
        }
        UUID harvestId = saleCreateDTO.getHarvestId();
        Optional<Harvest> harvest = harvestService.findHarvestById(harvestId);
        if (harvest.isEmpty()) {
            throw new EntityNotFoundException("Harvest with ID " + harvestId + " not found");
        }
        Sale updatedSale = saleMapper.toSale(saleCreateDTO);
        updatedSale.setId(id);
        updatedSale.setHarvest(harvest.get());
        return saleRepository.save(updatedSale);
    }


    @Override
    public void delete(UUID id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (sale.isEmpty()) {
            throw new EntityNotFoundException("Sale with ID " + id + " not found");
        }
        saleRepository.deleteById(id);
    }

    @Override
    public Optional<Sale> findSaleById(UUID id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (sale.isEmpty()) {
            throw new EntityNotFoundException("Sale with ID " + id + " not found");
        }
        return sale;

    }


}

package com.brief.citronix.service;

import com.brief.citronix.domain.Sale;
import com.brief.citronix.dto.SaleCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SaleService {
    Page<Sale> findAll(Pageable pageable);
    Sale save(SaleCreateDTO saleCreateDTO);
    Sale update(UUID id,SaleCreateDTO saleCreateDTO);
    void delete(UUID id);
    Optional<Sale> findSaleById(UUID id);
}

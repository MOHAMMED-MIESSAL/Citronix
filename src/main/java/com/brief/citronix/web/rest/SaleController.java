package com.brief.citronix.web.rest;


import com.brief.citronix.domain.Sale;
import com.brief.citronix.dto.SaleCreateDTO;
import com.brief.citronix.mapper.SaleMapper;
import com.brief.citronix.service.SaleService;
import com.brief.citronix.viewmodel.SaleVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final SaleMapper saleMapper;

    @GetMapping
    public ResponseEntity<Page<SaleVM>> getAllSales(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(saleService.findAll(pageable)
                .map(saleMapper::toSaleVM));
    }

    @PostMapping
    public ResponseEntity<SaleVM> createSale(@RequestBody @Valid SaleCreateDTO saleCreateDTO) {
        Sale sale = saleService.save(saleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleMapper.toSaleVM(sale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleVM> updateSale(@PathVariable UUID id, @RequestBody @Valid SaleCreateDTO saleCreateDTO) {
        Sale sale = saleService.update(id, saleCreateDTO);
        return ResponseEntity.ok(saleMapper.toSaleVM(sale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable UUID id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleVM> getSale(@PathVariable UUID id) {
        return saleService.findSaleById(id)
                .map(sale -> ResponseEntity.ok(saleMapper.toSaleVM(sale)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}

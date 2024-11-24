package com.brief.citronix.mapper;


import com.brief.citronix.domain.Sale;
import com.brief.citronix.dto.SaleCreateDTO;
import com.brief.citronix.viewmodel.SaleVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "harvestId", target = "harvest.id")
    Sale toSale(SaleCreateDTO saleCreateDTO);

    @Mapping(source = "harvest", target = "harvest")
    @Mapping(expression = "java(sale.calculateTotalPrice())", target = "revenue")
    SaleVM toSaleVM(Sale sale);
}

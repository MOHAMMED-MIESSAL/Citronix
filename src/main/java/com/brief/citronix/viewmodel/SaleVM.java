package com.brief.citronix.viewmodel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleVM {

    private LocalDateTime saleDate;
    private double unitPrice;
    private String clientName;
    private double revenue;

    private HarvestVM harvest;

}

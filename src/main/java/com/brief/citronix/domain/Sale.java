package com.brief.citronix.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime saleDate;
    private double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Harvest harvest;

    private String clientName;


    public double calculateTotalPrice() {
        return unitPrice * harvest.getTotalQuantity();
    }

}

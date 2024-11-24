package com.brief.citronix.domain;

import com.brief.citronix.enums.Season;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime harvestDate;

    private double totalQuantity;

    @Enumerated(EnumType.STRING)
    private Season season;

    @OneToMany(mappedBy = "harvest", fetch = FetchType.LAZY)
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest", fetch = FetchType.LAZY)
    private List<Sale> sales;
}
package com.brief.citronix.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tree tree;
}

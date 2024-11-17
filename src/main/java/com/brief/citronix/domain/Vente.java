package com.brief.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateVente;
    private double prixUnitaire;
    private double quantite;
    private String client;

    @ManyToOne
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;

    public double getRevenu() {
        return this.quantite * this.prixUnitaire;
    }
}

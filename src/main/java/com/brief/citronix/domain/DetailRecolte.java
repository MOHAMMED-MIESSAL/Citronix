package com.brief.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailRecolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantite;

    @ManyToOne
    @JoinColumn(name = "arbre_id")
    private Arbre arbre;

    @ManyToOne
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;
}

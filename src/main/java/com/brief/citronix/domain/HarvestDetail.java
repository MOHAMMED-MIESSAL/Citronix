package com.brief.citronix.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY )
    private Tree tree;

    @Override
    public String toString() {
        return "HarvestDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", harvest=" + harvest +
                ", tree=" + tree +
                '}';
    }
}

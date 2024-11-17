package com.brief.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePlantation;

    @ManyToOne
    @JoinColumn(name = "champ_id")
    private Champ champ;

    public int getAge() {
        return Period.between(this.datePlantation, LocalDate.now()).getYears();
    }

    public double getProductiviteAnnuelle() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        } else {
            return 20;
        }
    }
}

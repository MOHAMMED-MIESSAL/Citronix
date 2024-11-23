package com.brief.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime datePlantation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Field field;

    public int getAge() {
        LocalDateTime now = LocalDateTime.now();
        Period period = Period.between(datePlantation.toLocalDate(), now.toLocalDate());
        return period.getYears();
    }

    public double getAnnualProductivity() {
        int age = getAge();

        if (age > 20) {
            return 0;
        } else if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        } else {
            return 20;
        }
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", datePlantation=" + datePlantation +
                '}';
    }
}

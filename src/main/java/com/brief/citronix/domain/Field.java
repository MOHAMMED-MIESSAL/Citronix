package com.brief.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double area; // in hectares

    @ManyToOne(fetch = FetchType.LAZY)
    private Farm farm;

    @OneToMany(mappedBy = "field" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tree> trees;

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", area=" + area +
                '}';
    }
}
